package tests;

import main.Cliente;
import main.GerenciadoraClientes;
import main.IdadeNaoPermitidaException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class GerenciadoraClientesTest {

    private ArrayList<Cliente> clientes = new ArrayList<Cliente>();
    private GerenciadoraClientes gerenciadoraClientes = new GerenciadoraClientes(this.clientes);

    @BeforeEach
    public void setUp() {
        this.clientes = new ArrayList<Cliente>();
        this.gerenciadoraClientes = new GerenciadoraClientes(this.clientes);
    }

    @AfterEach
    public void tearDown() {
        this.clientes = null;
        this.gerenciadoraClientes = null;
    }

    @Test
    @DisplayName("getclientes")
    public void test_getclientes() {
        this.clientes.add(new Cliente(1, "Cliente 1", 11, "cliente1@furb.br", 1, true));
        this.clientes.add(new Cliente(2, "Cliente 2", 22, "cliente2@furb.br", 2, false));
        this.clientes.add(new Cliente(3, "Cliente 3", 33, "cliente3@furb.br", 3, true));

        var clientes = this.gerenciadoraClientes.getClientesDoBanco();

        assertEquals(clientes, this.clientes);
    }

    @Test
    @DisplayName("pesquisaCliente com cliente válido")
    public void test_pesquisaCliente_comClienteValido() {
        this.clientes.add(new Cliente(1, "Cliente 1", 11, "cliente1@furb.br", 1, true));
        this.clientes.add(new Cliente(2, "Cliente 2", 22, "cliente2@furb.br", 2, false));
        this.clientes.add(new Cliente(3, "Cliente 3", 33, "cliente3@furb.br", 3, true));

        var cliente = this.gerenciadoraClientes.pesquisaCliente(2);

        assertNotNull(cliente);
        assertEquals(this.clientes.get(1), cliente);
    }

    @Test
    @DisplayName("pesquisaCliente com cliente inválido")
    public void test_pesquisaCliente_comClienteInvalido() {
        this.clientes.add(new Cliente(1, "Cliente 1", 11, "cliente1@furb.br", 1, true));
        this.clientes.add(new Cliente(2, "Cliente 2", 22, "cliente2@furb.br", 2, false));
        this.clientes.add(new Cliente(3, "Cliente 3", 33, "cliente3@furb.br", 3, true));

        var cliente = this.gerenciadoraClientes.pesquisaCliente(9);

        assertNull(cliente);
    }

    @Test
    @DisplayName("adicionaCliente")
    public void test_adicionaCliente() {
        var clienteNovo = new Cliente(1, "Cliente 1", 11, "cliente1@furb.br", 1, true);

        this.gerenciadoraClientes.adicionaCliente(clienteNovo);

        assertEquals(1, this.clientes.size());
        assertEquals(clienteNovo, this.clientes.get(0));
    }


    @Test
    @DisplayName("removeCliente inexistente")

    public void test_removeCliente_inexistente() {
        this.clientes.add(new Cliente(1, "Cliente 1", 11, "cliente1@furb.br", 1, true));

        var flagRemovido = this.gerenciadoraClientes.removeCliente(1);

        assertEquals(true, flagRemovido);
    }


    @Test
    @DisplayName("removeCliente existente")

    public void test_removeCliente() {
        this.clientes.add(new Cliente(1, "Cliente 1", 11, "cliente1@furb.br", 1, true));

        var flagRemovido = this.gerenciadoraClientes.removeCliente(1);

        assertEquals(true, flagRemovido);
    }



    @Test
    @DisplayName("clienteAtivo existente")

    public void test_clienteAtivo() {
        this.clientes.add(new Cliente(1, "Cliente 1", 11, "cliente1@furb.br", 1, true));

        var flagAtivo = this.gerenciadoraClientes.clienteAtivo(1);

        assertEquals(true, flagAtivo);
    }

    @Test
    @DisplayName("clienteAtivo inexistente")

    public void test_clienteAtivo_inexistente() {

        var flagAtivo = this.gerenciadoraClientes.clienteAtivo(1);

        assertEquals(true, flagAtivo);
    }

    @Test
    @DisplayName("limpa")
    public void test_limpa() {
        this.clientes.add(new Cliente(1, "Cliente 1", 11, "cliente1@furb.br", 1, true));
        this.clientes.add(new Cliente(2, "Cliente 2", 22, "cliente2@furb.br", 2, false));
        this.clientes.add(new Cliente(3, "Cliente 3", 33, "cliente3@furb.br", 3, true));

        this.gerenciadoraClientes.limpa();

        assertEquals(0, this.clientes.size());
    }

    @Test
    @DisplayName("validaIdade com idade válida")
    public void test_validaIdade_comIdadeValida() throws IdadeNaoPermitidaException {
        int[] idades = {19, 18, 25, 55};


        for (int i = 0; i <= idades.length - 1; i++) {
            var flagIdade = this.gerenciadoraClientes.validaIdade(idades[i]);
            assertTrue(flagIdade);
        }

    }

    @Test
    @DisplayName("validaIdade com idade inválida")
    public void test_validaIdade_comIdadeInvalida() {
        Exception exception = assertThrows(
                IdadeNaoPermitidaException.class,
                () -> this.gerenciadoraClientes.validaIdade(88)
        );
        assertEquals(IdadeNaoPermitidaException.MSG_IDADE_INVALIDA, exception.getMessage());
    }
}