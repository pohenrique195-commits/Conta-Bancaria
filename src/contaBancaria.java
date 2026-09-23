
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class contaBancaria {

    protected int saldoDaConta;
    protected int numeroDaConta;
    protected ArrayList<String> extrato = new ArrayList<>();
    protected int totalInvestido;
    protected Correntista titular;

    public contaBancaria(int numeroDaConta, int saldoDaConta, Correntista titular) {
        this.numeroDaConta = numeroDaConta;
        this.saldoDaConta = saldoDaConta;
        this.titular = titular;
    }

    public int getTotalInvestido() {
        return this.totalInvestido;
    }

    public int getNumeroDaConta() {
        return this.numeroDaConta;
    }

    public int getSaldoDaConta() {
        return this.saldoDaConta;
    }

    public ArrayList<String> getExtrato() {
        return this.extrato;
    }

    public void saldo() {
        System.out.println("Seu saldo é de: " + this.saldoDaConta);
    }

    public void deposito() {
        int total;
        Scanner valor = new Scanner(System.in);
        System.out.println("Quanto voce quer depositar? ");
        try {
            total = valor.nextInt();
            if (total <= 0) {
                System.out.println("Valor invalido! Transação nao efetuada.");
                return;
            }
            this.saldoDaConta += total;
            String depositar = "Deposito: +R$" + total + " Saldo: " + this.saldoDaConta;
            this.extrato.add(depositar);
        } catch (InputMismatchException e) {
            System.out.println("Voce precisa digitar um numero! Transacao nao efetuada");
        }
    }

    protected int checarSaldo(String txt) {
        Scanner entrada = new Scanner(System.in);
        System.out.println(txt);

        while (true) {
            try {
                int numero = entrada.nextInt();
                if (numero > this.saldoDaConta) {
                    System.out.println("Valor é maior que o saldo! ");
                } else if (numero <= 0) {
                    System.out.println("Valor invalido! ");
                    return 0;
                } else {
                    return numero;
                }
            } catch (InputMismatchException e) {
                System.out.println("Voce precisa digitar um numero!");
            }
        }
    }

    public void saque() {
        int total = checarSaldo("Quanto voce quer sacar? Saldo atual: " + this.saldoDaConta);

        if (total <= 0) {
            return;
        }

        this.saldoDaConta -= total;
        this.extrato.add("Saque: -R$ " + total);
    }

    public void extrato() {
        for (String transacao : this.extrato) {
            System.out.println(transacao);
        }
    }

    public void investir(int valor) {
        if (this.saldoDaConta >= valor && valor > 0) {
            this.totalInvestido += valor;
            this.saldoDaConta -= valor;
            this.extrato.add("Investimento: -R$" + valor);
        }
    }

    public void saldoInvestimento() {
        System.out.println("A conta tem R$" + this.totalInvestido + " em investimentos");
    }

    public void contaAcessada() {
        Scanner entrada = new Scanner(System.in);
        int escolha = 0;
        while (escolha != 7) {
            System.out.println("====BANCO====\n 1 - Consultar saldo\n 2 -Depositar\n 3 - Sacar\n 4- Investir\n 5- Ver investimentos\n6 - Ver historico\n 7- Voltar\n Escolha: ");
            escolha = entrada.nextInt();
            switch (escolha) {
                case 1:
                    saldo();
                    break;
                case 2:
                    deposito();
                    break;
                case 3:
                    saque();
                    break;
                case 4:
                    int valorInvestir = this.titular.receberNumero("Quanto voce quer investir? Total disponivel: " + this.saldoDaConta);
                    investir(valorInvestir);
                    break;
                case 5:
                    saldoInvestimento();
                    break;
                case 6:
                    extrato();
                    break;
                case 7:
                    break;
            }
        }
    }
}

class contaCorrente extends contaBancaria{

    protected int limite;
    protected int limiteDisponivel;

    public contaCorrente(int numeroDaConta, int saldoDaConta, Correntista titular, int limite){
        super(numeroDaConta,saldoDaConta, titular);
        this.limite = limite;
        this.limiteDisponivel = limite;
    }

    public void debitarAnuidade(){
        this.saldoDaConta -=50;
    }

    @Override
    protected int checarSaldo(String txt) {
        Scanner entrada = new Scanner(System.in);
        System.out.println(txt);

        while (true) {
            try {
                int numero = entrada.nextInt();
                if (this.saldoDaConta >= 0 && numero > this.saldoDaConta + this.limiteDisponivel) {
                    System.out.println("Valor é maior que o saldo! ");
                } else if (this.saldoDaConta < 0 && numero > this.limiteDisponivel) {
                    System.out.println("Valor é maior que o saldo disponivel! ");
                } else if (numero <= 0) {
                    System.out.println("Valor invalido! ");
                    return 0;
                } else {
                    return numero;
                }
            } catch (InputMismatchException e) {
                System.out.println("Voce precisa digitar um numero!");
            }
        }
    }

    @Override
    public void saque() {
        int total = checarSaldo("Quanto voce quer sacar? Saldo atual: " + this.saldoDaConta
                + " Disponivel para limite: " + this.limiteDisponivel);

        if (total <= 0) {
            return;
        }

        if (this.saldoDaConta < 0) {
            System.out.println("Saque feito usando limite de R$ " + total);
            this.limiteDisponivel -= total;
        } else if (total > this.saldoDaConta) {
            int dif = total - this.saldoDaConta;
            System.out.println("Saque feito usando limite de R$ " + dif);
            this.limiteDisponivel -= dif;
        }

        this.saldoDaConta -= total;
        this.extrato.add("Saque: -R$ " + total);
    }

    @Override
    public void deposito() {
        int total;
        Scanner valor = new Scanner(System.in);
        System.out.println("Quanto voce quer depositar? ");
        try {
            total = valor.nextInt();
            if (total <= 0) {
                System.out.println("Valor invalido! Transação nao efetuada.");
                return;
            }

            if (this.saldoDaConta < 0 && this.saldoDaConta + total >= 0) {
                this.limiteDisponivel = this.limite;
            } else if (this.saldoDaConta < 0 && this.saldoDaConta + total < 0) {
                this.limiteDisponivel += total;
            }

            this.saldoDaConta += total;
            this.extrato.add("Deposito: +R$" + total + " Saldo: " + this.saldoDaConta);
        } catch (InputMismatchException e) {
            System.out.println("Voce precisa digitar um numero! Transacao nao efetuada");
        }
    }
}
class contaPoupanca extends contaBancaria{

    protected int taxaRendimento;

    public contaPoupanca(int numeroDaConta, int saldoDaConta, Correntista titular, int taxaRendimento){
        super(numeroDaConta,saldoDaConta, titular);
        this.taxaRendimento = taxaRendimento;
    }

    public void render(){
        this.saldoDaConta *= ((double) taxaRendimento /100);
    }


}