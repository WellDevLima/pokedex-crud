CREATE TABLE IF NOT EXISTS pokemon (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  nome VARCHAR(100) NOT NULL,
  tipo1 VARCHAR(50) NOT NULL,
  tipo2 VARCHAR(50),
  descricao TEXT NOT NULL,
  nome_arquivo_foto VARCHAR(255) NOT NULL
);