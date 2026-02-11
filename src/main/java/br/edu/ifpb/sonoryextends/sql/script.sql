CREATE TABLE audio_file (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    caminho_original TEXT NOT NULL,
    formato VARCHAR(100) NOT NULL,
    tamanho BIGINT NOT NULL
);

CREATE TABLE conversion (
    id SERIAL PRIMARY KEY,
    audio_file_id INT,
    formato_origem VARCHAR(100) NOT NULL,
    formato_destino VARCHAR(100) NOT NULL,
    caminho_convertido TEXT NOT NULL,
    data_conversao TIMESTAMP,

    CONSTRAINT fk_conversion_audio FOREIGN KEY (audio_file_id) REFERENCES audio_file (id)
);

CREATE TABLE user_action (
    id SERIAL PRIMARY KEY,
    descricao TEXT NOT NULL,
    data_hora TIMESTAMP
);