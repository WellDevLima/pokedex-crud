CREATE TABLE IF NOT EXISTS aluno(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(100) NOT NULL,
    cpf  VARCHAR(11)  NOT NULL 
);

