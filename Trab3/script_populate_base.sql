
-- Input de dados


insert into mensagens
(id, msg_text)
values
(1001,'Sistema iniciado.'),
(1002,'Sistema encerrado.'),
(2001,'Autenticação etapa 1 iniciada.'),
(2002,'Autenticação etapa 1 encerrada.'),
(2003,'Login name <login_name> identificado com acesso liberado.'),
(2004,'Login name <login_name> identificado com acesso bloqueado.'),
(2005,'Login name <login_name> não identificado.'),
(3001,'Autenticação etapa 2 iniciada para <login_name>.'),
(3002,'Autenticação etapa 2 encerrada para <login_name>.'),
(3003,'Senha pessoal verificada positivamente para <login_name>.'),
(3004,'Senha pessoal verificada negativamente para <login_name>.'),
(3005,'Primeiro erro da senha pessoal contabilizado para <login_name>.'),
(3006,'Segundo erro da senha pessoal contabilizado para <login_name>.'),
(3007,'Terceiro erro da senha pessoal contabilizado para <login_name>.'),
(3008,'Acesso do usuario <login_name> bloqueado pela autenticação etapa 2.'),
(4001,'Autenticação etapa 3 iniciada para <login_name>.'),
(4002,'Autenticação etapa 3 encerrada para <login_name>.'),
(4003,'One-time password verificada positivamente para <login_name>.'),
(4004,'Primeiro erro da one-time password contabilizado para <login_name>.'),
(4005,'Segundo erro da one-time password contabilizado para <login_name>.'),
(4006,'Terceiro erro da one-time password contabilizado para <login_name>.'),
(4007,'Acesso do usuario <login_name> bloqueado pela autenticação etapa 3.'),
(5001,'Tela principal apresentada para <login_name>.'),
(5002,'Opção 1 do menu principal selecionada por <login_name>.'),
(5003,'Opção 2 do menu principal selecionada por <login_name>.'),
(5004,'Opção 3 do menu principal selecionada por <login_name>.'),
(5005,'Opção 4 do menu principal selecionada por <login_name>.'),
(6001,'Tela de cadastro apresentada para <login_name>.'),
(6002,'Botão cadastrar pressionado por <login_name>.'),
(6003,'Botão voltar de cadastrar para o menu principal pressionado por <login_name>.'),
(7001,'Tela de consulta apresentada para <login_name>.'),
(7002,'Botão voltar de consultar para o menu principal pressionado por <login_name>.'),
(7003,'Botão Listar de consultar pressionado por <login_name>.'),
(7004,'Caminho da chave privada inválido fornecido por <login_name>.'),
(7005,'Frase secreta inválida fornecida por <login_name>.'),
(7006,'Caminho de pasta inválido fornecido por <login_name>.'),
(7007,'Lista de arquivos apresentada para <login_name>.'),
(7008,'Arquivo <arq_name> selecionado por <login_name> para decriptação.'),
(7009,'Arquivo <arq_name> decriptado com sucesso para <login_name>.'),
(7010,'Arquivo <arq_name> verificado (integridade e autenticidade) com sucesso para <login_name>.'),
(7011,'Falha na decriptação do arquivo <arq_name> para <login_name>.'),
(7012,'Falha na verificação do arquivo <arq_name> para <login_name>.'),
(8001,'Tela de saída apresentada para <login_name>.'),
(8002,'Botão sair pressionado por <login_name>.'),
(8003,'Botão voltar de sair para o menu principal pressionado por <login_name>.');


INSERT INTO grupos (grupo_name, created_at)
values
('Administrador', now()),
('Usuario', now());
