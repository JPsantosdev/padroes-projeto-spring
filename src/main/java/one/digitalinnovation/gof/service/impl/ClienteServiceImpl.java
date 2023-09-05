package one.digitalinnovation.gof.service.impl;

import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.ClienteRepository;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.EnderecoRepository;
import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    // Singleton: Injetar os componentes Spring com AutoWired.
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private ViaCepService viaCepService;

    // Strategy: Implementar os métodos definidos na interface.
    // Facade: Abstrair as integrações com subsistemas, provendo uma interface simples.

    @Override
    public Iterable<Cliente> buscarTodos() {
        // FIXME: Buscar todos os clientes. (FEITO)
        return clienteRepository.findAll();
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        // FIXME: Buscar clientes por ID.
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente;
    }

    @Override
    public void inserir(Cliente cliente) {
        preencherClienteComCep(cliente);
    }

    private void preencherClienteComCep(Cliente cliente) {
        // FIXME: Verificar se o endereco do cliente já existe (Via CEP) (feito)
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            // FIXME: Caso não exista, integrar com o ViaCEP e persistir o retorno (feito)
            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return null;
        });
        cliente.setEndereco(endereco);
        // FIXME: Inserir cliente, vinculando o endereço (novo ou existente)
        clienteRepository.save(cliente);
    }

    @Override
    public void atualizar(Long id, Cliente cliente) {
        // FIXME Buscar Cliente por ID
        Optional<Cliente> clienteDB = clienteRepository.findById(id);
        if (clienteDB.isPresent()) {
            // FIXME Verificar se o endereco já está cadastrado (pelo CEP)
            // FIXME Caso não exista, integrar com o ViaCEP e persistir o retorno
            // FIXME alterar o cliente, vindulando o endereço (novo ou existente)
            preencherClienteComCep(cliente); // Esse método resolve todos os FIXMEs solicitados
        }
    }

    @Override
    public void deletar(Long id) {
        // FIXME deletar cliente por ID.
        clienteRepository.deleteById(id);
    }

}
