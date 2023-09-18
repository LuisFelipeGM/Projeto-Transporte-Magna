package br.com.magna.trainees.transporte.services;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import br.com.magna.trainees.transporte.dtos.PassageiroDto;
import br.com.magna.trainees.transporte.models.PassageiroModel;
import br.com.magna.trainees.transporte.repositories.PassageiroRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PassageiroService extends EntityService<PassageiroModel> {

	private final PassageiroRepository usuarioRepository;

	PassageiroService(JpaRepository<PassageiroModel, Long> repository, PassageiroRepository usuarioRepository) {
		super(repository);
		this.usuarioRepository = usuarioRepository;
	}

	public PassageiroModel adicionaUsuario(PassageiroDto usuarioDto){
		try {
			PassageiroModel usuario = new PassageiroModel();
			BeanUtils.copyProperties(usuarioDto, usuario);

			log.info("Cadastrando novo Usuario");

			return repository.save(usuario);

		} catch (DataIntegrityViolationException e) {
            log.error("Erro ao salvar o novo usuário: Restrição exclusiva violada.");
            throw new RuntimeException("Erro ao salvar o novo usuário: Este CPF já está cadastrado no sistema.");
        } catch (Exception e) {
            log.error("Erro ao copiar as propriedades do DTO para o modelo de Usuario: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar o novo usuario.");
        }
	}

	public PassageiroModel putUsuario(PassageiroDto usuarioDto, Long id){
			try {
			Optional<PassageiroModel> usuarioOptional = usuarioRepository.findById(id);
			if (usuarioOptional.isPresent()) {
				PassageiroModel usu = usuarioOptional.get();
                BeanUtils.copyProperties(usuarioDto, usu);

                log.info("Atualizando Usuario de ID: " + id);
                return repository.save(usu);
			} else {
				throw new RuntimeException("Erro ao salvar o novo usuario.");
			}
			} catch (Exception e) {
				log.error("Erro ao copiar as propriedades do DTO para o modelo de Usuario: " + e.getMessage());
            	throw new RuntimeException("Erro ao salvar o novo usuario.");
			}




	}

}
