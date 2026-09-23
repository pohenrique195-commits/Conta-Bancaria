

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Correntista {

    private long cpf;
    private String nome;
    private ArrayList<contaBancaria> contas;

    public Correntista(long cpf, String nome) {
        this.cpf = cpf;
        this.nome = nome;
        this.contas = new ArrayList<>();
    }

    public String getNome() {
        return this.nome;
    }

    public long getCpf() {
        return this.cpf;
    }

    public ArrayList<contaBancaria> getContas() {
        return this.contas;
    }

    public int receberNumero(String txt) {
        Scanner entrada = new Scanner(System.in);
        System.out.println(txt);

        while (true) {
            try {
                return entrada.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Voce precisa digitar um numero!");
            }
        }
    }

    public void criarConta() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Qual o numero da nova conta? ");
        int numeroConta = entrada.nextInt();
        for (contaBancaria conta : this.contas) {
            if (numeroConta == conta.getNumeroDaConta()) {
                System.out.println("Conta ja existe!");
                return;
            }
        }
        int saldo = receberNumero("Qual o saldo inicial da conta? ");
        int receber;
        System.out.println("A conta é corrente ou poupança? Clique 1 para corrente 0 para poupança");
        receber = entrada.nextInt();
        if(receber != 0 && receber!= 1){
            System.out.println("opcao nao disponivel!");
            return;
        }
        contaBancaria novaConta;

        if(receber == 0){
            System.out.println("Qual a taxa de rendimento da conta?");
            int rendimento = entrada.nextInt();
            novaConta = new contaPoupanca(numeroConta, saldo, this, rendimento);
        }
        else{
            System.out.println("Qual o limite da conta? ");
            int limite = entrada.nextInt();
            novaConta = new contaCorrente(numeroConta, saldo, this, limite);
        }
        this.contas.add(novaConta);
    }

    public void acessarConta() {
        Scanner entrada = new Scanner(System.in);
        System.out.println("Qual o numero da conta? ");
        int entrarConta = entrada.nextInt();
        for (contaBancaria conta : this.contas) {
            if (conta.getNumeroDaConta() == entrarConta) {
                conta.contaAcessada();
                return;
            }
        }
        System.out.println("Conta nao existente!");
    }

    public void correntistaAcessado() {
        Scanner entrada = new Scanner(System.in);
        int escolha = 0;
        while (escolha != 3) {
            System.out.println("====BANCO====\n 1 - Cadastrar nova conta\n 2 - Acessar conta\n 3 - Sair\n Escolha: ");
            escolha = entrada.nextInt();
            switch (escolha) {
                case 1:
                    criarConta();
                    break;
                case 2:
                    acessarConta();
                    break;
                case 3:
                    System.out.println("Encerrando...");
                    break;
            }
        }
    }
}