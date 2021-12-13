## updated 12/12 by Nuno :)

# problemas:
 	RESOLVIDO menu dentro do consultar aparenta funcionar apenas com input errado. how ironic.

# afazeres:
	criar runRegisto com formulario a pedir o que diz no enunciado
	descobrir como associar os servicos a um IP e PORT para poderem ser registados
	fazer o seguimento do consultar (para onde vai depois de escolher sockets ou rmi) - lista de servicos de cada um tem de atualizar live
	descobir como criar tickets e fazer essa criacao quando o cliente escolhe o servico (simplesmente entra no servico e deixa interagir com ele durante o tempo do ticket?)

# melhoramentos (detalhes):
	limpar a consola sempre que se printa um novo menu, dando a aparencia de que a aplicacao corre sempre no mesmo sitio, com cada menu novo a substituir o antigo. we are # # approaching GUI
	riscar a ideia anterior e simplesmente fazer uma GUI. how hard can it be?
	sera possivel criar serversocker no server.java e passar para o SI e ST como parametro? I think yes but how do?
	ideia estupida: criar uma funcao para ler menus com a cena do while, que chamamos sempre que queremos ler menus 

# stor:
	SI dá ao cliente o ip e porto do ST
	Cliente usa essa ip e porto para se conectar ao ST
	cliente envia o seu cc+hash para o ST verificar
	ST.consulta dá a lista de servicos que já foram registados e permite ao cliente conectar-se a cada um, dando um timestamp que depois é validado pelo servico
	ST.registo simplesmente guarda a informacao do servico e depois apresenta no consultar (usar structs?)

	necessita do ip e port do ST para se conectar!!! usar default_address
