package br.com.magna.trainees.transporte.services;

import br.com.magna.trainees.transporte.dtos.ConexaoDto;
import br.com.magna.trainees.transporte.models.ConexaoModel;
import br.com.magna.trainees.transporte.models.EstacaoModel;
import br.com.magna.trainees.transporte.models.LinhaModel;
import br.com.magna.trainees.transporte.repositories.ConexaoRepository;
import br.com.magna.trainees.transporte.repositories.EstacaoRepository;
import br.com.magna.trainees.transporte.repositories.LinhaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConexaoService extends EntityService<ConexaoModel>{

    private static final Logger log = LoggerFactory.getLogger(CartaoService.class);

    private final ConexaoRepository conexaoRepository;
    private final LinhaRepository linhaRepository;
    private final EstacaoRepository estacaoRepository;

    ConexaoService(JpaRepository<ConexaoModel, Long> repository, ConexaoRepository conexaoRepository,
                   LinhaRepository linhaRepository, EstacaoRepository estacaoRepository) {
        super(repository);
        this.conexaoRepository = conexaoRepository;
        this.linhaRepository = linhaRepository;
        this.estacaoRepository = estacaoRepository;
    }

    public ConexaoModel adicionaConexaoEstacaoLinha(ConexaoDto conexaoDto){
        try {
            Optional<LinhaModel> optionalLinha = linhaRepository.findById(conexaoDto.idLinha());
            if (optionalLinha.isPresent()) {
                LinhaModel linha = optionalLinha.get();
                Optional<EstacaoModel> optionalEstacao = estacaoRepository.findById(conexaoDto.idEstacao());
                if (optionalEstacao.isPresent()) {
                    EstacaoModel estacao = optionalEstacao.get();
                    ConexaoModel conexao = new ConexaoModel();
                    conexao.setLinha(linha);
                    conexao.setEstacao(estacao);

                    log.info("Cadastrando nova Conexão");
                    return repository.save(conexao);
                } else {
                    throw new Exception("Estação não encontrada");
                }
            } else {
                throw new Exception("Linha não encontrada");
            }
        } catch (Exception e) {
            log.error("Erro ao adicionar nova Conexão: " + e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }


}
