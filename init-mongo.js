db.createUser({
    user: "meu_usuario",
    pwd: "minha_senha",
    roles: [
        {
            role: "readWrite",
            db: "desafio_backend"
        }
    ]
});
  