# Cloud AWS
provider "aws" {
  region                        = "us-east-1"
  skip_credentials_validation   = true
  skip_metadata_api_check       = true
}

# Instances And Services
resource "aws_s3_bucket" "catalog_bucket" { bucket = "meu-catalogo-json" }
resource "aws_sqs_queue" "catalog_queue" { name = "catalog-change-queue" }
resource "aws_instance" "docker_instance" {
  # Ubuntu Instance: https://console.aws.amazon.com/ec2/home?region=us-east-1#launchAmi=ami-084568db4383264d4
  ami                         = "ami-084568db4383264d4"
  instance_type               = "t2.micro"
  key_name                    = aws_key_pair.key_pair.key_name
  
  # Permissions
  security_groups             = [aws_security_group.allow_http_ssh_mongo.name]
  iam_instance_profile        = aws_iam_instance_profile.ec2_s3_sqs_profile.name 

  associate_public_ip_address = true # Force Public IP


  tags = {  Name = "DockerInstance" }


  # Config and Running Application

    # 1° File Input for Docker Run
    provisioner "file" {
        source      = "./docker-compose.aws.yml"
        destination = "/home/ubuntu/docker-compose.yml"
        connection {
            type        = "ssh"
            user        = "ubuntu"
            private_key = file("~/.ssh/id_rsa")  # Substitua pelo caminho correto da sua chave privada
            host        = self.public_ip
        }
    }
    # 1.1° File Input For Config MongoDB
    provisioner "file" {
        source      = "./init-mongo.js"
        destination = "/home/ubuntu/init-mongo.js"
        connection {
            type        = "ssh"
            user        = "ubuntu"
            private_key = file("~/.ssh/id_rsa")  # Substitua pelo caminho correto da sua chave privada
            host        = self.public_ip
        }
    }
    # 1.2° File Input For app.jar Running
    provisioner "file" {
        source      = "./desafio_backend/target/desafio_backend-0.0.1-SNAPSHOT.jar"
        destination = "/home/ubuntu/app.jar"
        connection {
            type        = "ssh"
            user        = "ubuntu"
            private_key = file("~/.ssh/id_rsa")  # Substitua pelo caminho correto da sua chave privada
            host        = self.public_ip
        }
    }


    # 2° Install Docker and Run Docker Compose with Application
    provisioner "remote-exec" {
        inline = [
        "sudo apt update -y",
        "sudo apt install -y docker.io",
        "sudo apt install -y docker-compose",
        "cd /home/ubuntu/",
        "sudo docker-compose -f /home/ubuntu/docker-compose.yml up -d"
        ]

        # Permit ssh in user: ubuntu
        connection {
            type        = "ssh"
            user        = "ubuntu"
            private_key = file("~/.ssh/id_rsa")
            host        = self.public_ip
        }
    }
}

# Politics And Security Group
data "aws_caller_identity" "current" {}
resource "aws_security_group" "allow_http_ssh_mongo" {
  name        = "allow_http_ssh_mongo"
  description = "Allow HTTP, SSH, and MongoDB access"

  # SSH *.*
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  
  # http *.*
  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # mongodb *.*
  ingress {
    from_port   = 27017
    to_port     = 27017
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  
  # Allow Traffic output *.*
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

# IAM Config for EC2
resource "aws_iam_instance_profile" "ec2_s3_sqs_profile" {
  name = "ec2-s3-sqs-profile"
  role = aws_iam_role.ec2_s3_sqs_role.name
}
resource "aws_iam_role_policy_attachment" "attach_s3_sqs_policy_to_role" {
  policy_arn = aws_iam_policy.ec2_s3_sqs_policy.arn
  role       = aws_iam_role.ec2_s3_sqs_role.name
}
resource "aws_iam_role" "ec2_s3_sqs_role" {
  name               = "ec2-s3-sqs-role"
  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action    = "sts:AssumeRole"
        Effect    = "Allow"
        Principal = {
          Service = "ec2.amazonaws.com"
        }
      }
    ]
  })
}
resource "aws_iam_policy" "ec2_s3_sqs_policy" {
  name        = "ec2-s3-sqs-policy"
  description = "IAM policy to allow EC2 access to S3 and SQS"
  policy      = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Action = [
          "s3:GetObject",
          "s3:PutObject",
          "s3:ListBucket"
        ]
        Effect   = "Allow"
        Resource = [
          "arn:aws:s3:::meu-catalogo-json",
          "arn:aws:s3:::meu-catalogo-json/*"
        ]
      },
      {
        Action = [
          "sqs:SendMessage",
          "sqs:ReceiveMessage",
          "sqs:DeleteMessage"
        ]
        Effect   = "Allow"
        Resource = "arn:aws:sqs:us-east-1:${data.aws_caller_identity.current.account_id}:catalog-change-queue"
      }
    ]
  })
}



resource "aws_key_pair" "key_pair" {
  key_name   = "minha-chave-ssh"
  public_key = file("~/.ssh/id_rsa.pub")
}

# Outputs
output "instance_ip" {
  # Output Public IP for Instance Docker
  value = aws_instance.docker_instance.public_ip
}

