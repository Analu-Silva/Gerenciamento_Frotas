import java.util.Scanner;
import java.util.List;

public class ViewCadVeiculo {
    private static ServiceVeiculo service = new ServiceVeiculo();
    static Scanner input = new Scanner(System.in);

    public static void limparTela() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public static void aguardarEnter() {
        System.out.print("Pressione Enter para continuar...");
        input.nextLine();
    }

    private static int inputNumerico(String mensagem) {
        int valor = 0;
        boolean entradaValida = false;
        System.out.print(mensagem);
        do {
            String valorStr = input.nextLine();
            try {
                valor = Integer.parseInt(valorStr);
                entradaValida = true;
            } catch (Exception e) {
                System.out.println("ERRO. Valor informado deve ser um número Inteiro");
            }
        } while (!entradaValida);
        return valor;
    }

    private static void cadastrar(){
        limparTela();
        Veiculo novoVeiculo = new Veiculo();
        System.out.println("===== Cadastro de novo veiculo =====");
        int tipo = inputNumerico("Qual o tipo de veículo: (1) Carro - (2) Moto  ");

        System.out.println("Digite a marca: ");
        String marca = input.nextLine();

        System.out.println("Digite o modelo: ");
        String modelo = input.nextLine();

        System.out.println("Digite a placa: ");
        String placa = input.nextLine();
        
        int ano = inputNumerico("Digite o ano: ");

        if (tipo == 1) {
            Carro carro = new Carro();
            carro.setMarca(marca);
            carro.setModelo(modelo);
            carro.setPlaca(placa);
            carro.setAno(ano);

            int numeroPortas = inputNumerico("Digite o número de portas: ");
            if (numeroPortas <= 0 || numeroPortas > 5) {
                System.out.println("O número de portas deve ser maior que zero e menor que cinco");
            }
            carro.setNumeroPortas(numeroPortas);
            novoVeiculo = carro;

        } else if(tipo == 2) {
            Moto moto = new Moto();
            moto.setMarca(marca);
            moto.setModelo(modelo);
            moto.setPlaca(placa);
            moto.setAno(ano);

            boolean partidaValida = false;
            while (!partidaValida) {
                System.out.println("É partida eletricada? (Digite 's' para sim ou 'n' para não");
                String partida = input.nextLine().toLowerCase();

                if (partida.equals("s")){
                    moto.setPartidaEletrica(true);
                    partidaValida = true;
                } else if (partida.equals("n")) {
                    moto.setPartidaEletrica(false);
                    partidaValida = true;
                } else {
                    System.out.println("Entrada inválida, por favor, digite 's' ou 'n'...");
                }
            }
            novoVeiculo = moto;
        }

        try{
            service.cadastrar(novoVeiculo);
            System.out.println("Veiculo cadastrado com sucesso!");
        } catch(Exception e){
            System.out.println("ERRO: " + e.getMessage());
        }

        aguardarEnter();
    }

    private static void removerPorPlaca() {
        limparTela();
        System.out.println("==== Remover Veiculo por Placa ====");
        System.out.println("Informe a placa que deseja remover: ");
        String placa = input.nextLine();

        try {
            service.removerPorPlaca(placa);
            System.out.println("Veiculo removido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        aguardarEnter();
    }

    private static void listar() {
        System.out.println("===== Lista de Veículos =====");
        List<Veiculo> veiculos = service.findAll();
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
        } else {
            for (Veiculo veiculo : veiculos) {
                if (veiculo instanceof Carro) {
                    Carro carro = (Carro) veiculo;
                    System.out.println("Tipo: Carro | Marca: " + carro.getMarca() + " | Modelo: " + carro.getModelo() +
                            " | Ano: " + carro.getAno() + " | Placa: " + carro.getPlaca() +
                            " | Portas: " + carro.getNumeroPortas());
                } else if (veiculo instanceof Moto) {
                    Moto moto = (Moto) veiculo;
                    System.out.println("Tipo: Moto | Marca: " + moto.getMarca() + " | Modelo: " + moto.getModelo() +
                            " | Ano: " + moto.getAno() + " | Placa: " + moto.getPlaca() +
                            " | Partida Elétrica: " + (moto.isPartidaEletrica() ? "Sim" : "Não"));
                }
            }
        }
        aguardarEnter();
    }

    private static void pesquisarVeiculo() {
        limparTela();
        System.out.print("Informe a placa do veículo a ser pesquisado: ");
        String placa = input.nextLine();
        try {
            Veiculo veiculo = service.findByPlaca(placa);
            if (veiculo != null) {
                if (veiculo instanceof Carro) {
                    Carro carro = (Carro) veiculo;
                    System.out.println("Tipo: Carro | Marca: " + carro.getMarca() + " | Modelo: " + carro.getModelo() +
                    " | Ano: " + carro.getAno() + " | Placa: " + carro.getPlaca() +
                    " | Portas: " + carro.getNumeroPortas());
                } else if (veiculo instanceof Moto) {
                    Moto moto = (Moto) veiculo;
                    System.out.println("Tipo: Moto | Marca: " + moto.getMarca() + " | Modelo: " + moto.getModelo() +
                    " | Ano: " + moto.getAno() + " | Placa: " + moto.getPlaca() +
                    " | Partida Elétrica: " + (moto.isPartidaEletrica() ? "Sim" : "Não"));
                }
            }
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
        aguardarEnter();
    }


    public static void main(String[] args) {
        String menu = """
                SISTEMA DE GERENCIAMENTO DE FROTAS
                Menu de Opções:
                1 - Cadastrar novo Veículo;
                2 - Listar todos Veículos cadastrados;
                3 - Pesquisar Veículo pela placa;
                4 - Remover Veículo;
                0 - Sair;
                Digite a opção desejada: 
                """;
        int opcao;
        do {
            limparTela();
            opcao = inputNumerico(menu);
            switch (opcao) {
                case 0:
                    System.out.println("VOLTE SEMPRE!!!");
                    break;
                case 1:
                    cadastrar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    pesquisarVeiculo();
                    break;
                case 4:
                    removerPorPlaca();
                    break;
                default:
                    System.out.println("Opção Inválida!!!");
                    aguardarEnter();
                    break;
            }
        } while (opcao != 0);
    }
}