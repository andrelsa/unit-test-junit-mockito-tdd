package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.Date;

import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LocacaoServiceTest {

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    public LocacaoServiceTest() {
    }

    @Test
    public void testeLocacao() throws Exception {
        //cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuário 1");
        Filme filme = new Filme("Filme 2", 2, 5.0);

        //ação
        Locacao locacao = service.alugarFilme(usuario, filme);

        //verificação
        error.checkThat(locacao.getValor(), is(equalTo(5.0)));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
    }

    @Test(expected = Exception.class)
    public void testLocacao_filmeSemEstoque() throws Exception {
        //cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuário 1");
        Filme filme = new Filme("Filme 2", 0, 5.0);

        //ação
        service.alugarFilme(usuario, filme);
    }

    @Test
    public void testLocacao_filmeSemEstoque2() {
        //cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuário 1");
        Filme filme = new Filme("Filme 2", 0, 5.0);

        //ação
        try {
            service.alugarFilme(usuario, filme);
            Assert.fail("Deveria ter lançado uma exceção");
        } catch (Exception e) {
            e.printStackTrace();
            assertThat(e.getMessage(), is("Filme sem estoque"));
        }
    }

    @Test
    public void testLocacao_filmeSemEstoque3() throws Exception {
        //cenário
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuário 1");
        Filme filme = new Filme("Filme 2", 0, 5.0);

        exception.expect(Exception.class);
        exception.expectMessage("Filme sem estoque");

        //ação
        service.alugarFilme(usuario, filme);
    }
}
