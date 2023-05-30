package tests;

import main.ContaCorrente;
import main.GerenciadoraContas;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.runners.Parameterized;


import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.Assert.*;


public class GerenciadoraContasTest {

    private ArrayList<ContaCorrente> contas = new ArrayList<ContaCorrente>();
    private GerenciadoraContas gerenciadoraContas = new GerenciadoraContas(this.contas);

    @BeforeEach
    public void setUp() {
        this.contas = new ArrayList<ContaCorrente>();
        this.gerenciadoraContas = new GerenciadoraContas(this.contas);
    }

    @AfterEach
    public void tearDown() {
        this.contas = null;
        this.gerenciadoraContas = null;
    }

    @Test
    @DisplayName("getcontas")
    public void test_getcontas() {
        this.contas.add(new ContaCorrente(1, 11.11, true));
        this.contas.add(new ContaCorrente(2, 22.22, false));
        this.contas.add(new ContaCorrente(3, 33.33, true));

        var contas = this.gerenciadoraContas.getContasDoBanco();

        assertEquals(contas, this.contas);
    }

    @Test
    @DisplayName("pesquisaConta com conta válida")
    public void test_pesquisaConta_comContaValida() {
        this.contas.add(new ContaCorrente(1, 11.11, true));
        this.contas.add(new ContaCorrente(2, 22.22, false));
        this.contas.add(new ContaCorrente(3, 33.33, true));

        var conta = this.gerenciadoraContas.pesquisaConta(2);

        assertNotNull(conta);
        assertEquals(this.contas.get(1), conta);
    }

    @Test
    @DisplayName("pesquisaConta com conta inválida")
    public void test_pesquisaConta_comContaInvalida() {
        this.contas.add(new ContaCorrente(1, 11.11, true));
        this.contas.add(new ContaCorrente(2, 22.22, false));
        this.contas.add(new ContaCorrente(3, 33.33, true));

        var conta = this.gerenciadoraContas.pesquisaConta(9);

        assertNull(conta);
    }

    @Test
    @DisplayName("adicionaConta")
    public void test_adicionaConta() {
        var contaNova = new ContaCorrente(1, 11.11, true);

        this.gerenciadoraContas.adicionaConta(contaNova);

        assertEquals(1, this.contas.size());
        assertEquals(contaNova, this.contas.get(0));
    }


    @Test
    @DisplayName("removeConta existente")
    public void removeContaExistente_Test()
    {
        this.gerenciadoraContas.adicionaConta(new ContaCorrente(12, 234.2, true));
        int contaExistente = 12;
        assertTrue(this.gerenciadoraContas.removeConta(contaExistente));
        assertEquals(5, this.gerenciadoraContas.getContasDoBanco().size());
    }

    @Test
    @DisplayName("removeConta inexistente")
    public void removeContaInexistente_Test()
    {
        int contaInexistente = 88;
       assertFalse(this.gerenciadoraContas.removeConta(contaInexistente));
       assertEquals(5, this.gerenciadoraContas.getContasDoBanco().size());
    }

    @Test
    @DisplayName("contaAtiva existente")
    public void test_contaAtiva() {
        this.contas.add(new ContaCorrente(1, 45.90, true));

        var flagAtiva = this.gerenciadoraContas.contaAtiva(this.contas.get(0).getId());

        assertEquals(this.contas.get(0).isAtiva(), flagAtiva);
    }

    @Test
    @DisplayName("contaAtiva inexistente")
    public void test_contaAtiva_inexistente() {

        assertFalse(this.gerenciadoraContas.contaAtiva(98));
    }


    @Test
    @DisplayName("transfereValor")
    public void test_transfereValor() {
        this.contas.add(new ContaCorrente(1, 45, true));
        this.contas.add(new ContaCorrente(2, 45, false));

        var flagSucesso = this.gerenciadoraContas.transfereValor(1, 45, 2);

       assertEquals(0.0 , this.gerenciadoraContas.pesquisaConta(1 ).getSaldo(), 0.001);
       assertEquals(90, this.gerenciadoraContas.pesquisaConta(2).getSaldo(), 0.001);


    }
}