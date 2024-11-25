create table topicos(

id bigint not null auto_increment,
titulo varchar(250) not null,
mensagem varchar(250) not null,
data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
estado_topico varchar(250) null,
autor varchar(250) not null,
curso varchar(250) not null,

primary key(id)
);
