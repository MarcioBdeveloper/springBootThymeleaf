package com.springBootone.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springBootone.model.Usuario;
import com.springBootone.repository.UsuarioRepository;

@Service
public class UsuarioServico implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public List<Usuario> listaUsuarios(){
		return  (List<Usuario>) this.usuarioRepository.findAll();
	}
	
	public void salvar(Usuario usuario){
		this.usuarioRepository.save(usuario);
	}
}
