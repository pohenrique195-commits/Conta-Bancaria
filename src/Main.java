
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private ArrayList<Correntista> correntistas = new ArrayList<>();

    public void criarCorrentista() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Qual o cpf do novo correntista? ");
        try {
            long numeroCpf = entrada.nextLong();
            entrada.nextLine();
            for (Correntista correntista : correntistas) {
                if (numeroCpf == correntista.getCpf()) {
                    System.out.println("Correntista ja cadastrado! ");
                    return;
                }
            }
            System.out.println("Qual o nome do novo correntista? ");
            String nome = entrada.nextLine();
            Correntista novoCorrentista = new Correntista(numeroCpf, nome);
            correntistas.add(novoCorrentista);
        } catch (InputMismatchException e) {
            System.out.println("Digite somente numeros!");
        }
    }

    public void acessarCorrentista() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Qual o cpf do correntista? ");
        try {
            long entrarCorrentista = entrada.nextLong();
            for (Correntista checagem : correntistas) {
                long numeroDeCpf = checagem.getCpf();
                if (numeroDeCpf == entrarCorrentista) {
                    checagem.correntistaAcessado();
                    return;
                }
            }
            System.out.println("Correntista nao existente!");
        } catch (InputMismatchException e) {
            System.out.println("Digite somente numeros!");
        }
    }

    public void mostrarInvestimentos() {
        for (Correntista correntista : correntistas) {
            for (contaBancaria conta : correntista.getContas()) {
                System.out.println("O correntista " + correntista.getNome() + " tem R$"
                        + conta.getTotalInvestido() + " na conta de numero " + conta.getNumeroDaConta());
            }
        }
    }

    public void executar() {
        Scanner entrada = new Scanner(System.in);
        int escolha = 0;

        while (escolha != 3) {
            System.out.println("====BANCO====\n 1 - Cadastrar novo correntista\n 2 - Logar como correntista\n 3 - Ver todos os investimentos no banco\n 4 - Sair \n Escolha: ");
            try {
                escolha = entrada.nextInt();
                switch (escolha) {
                    case 1:
                        criarCorrentista();
                        break;
                    case 2:
                        acessarCorrentista();
                        break;
                    case 3:
                        mostrarInvestimentos();
                        break;
                    case 4:
                        System.out.println("Encerrando...");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Valor precisa ser numero!");
            }
        }
    }

    public static void main(String[] args) {
        new Main().executar();
    }
}