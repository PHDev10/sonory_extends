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

CREATE TABLE conversion_history(
	id SERIAL PRIMARY KEY,
	nome_original VARCHAR(100) NOT NULL,
	formato_original VARCHAR(100) NOT NULL,
	formato_conversao VARCHAR(100) NOT NULL,
	pacote_saida TEXT,
	data_conversao TIMESTAMP,
    user_id INT,
	status VARCHAR(50),

	CONSTRAINT fk_conversion_history_user_profile FOREIGN KEY (user_id) REFERENCES user_profile (id)
);

CREATE TABLE user_profile(
     id SERIAL PRIMARY KEY,
     nome VARCHAR(100) NOT NULL UNIQUE
);