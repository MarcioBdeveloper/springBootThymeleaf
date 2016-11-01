package com.springBootone.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springBootone.model.Usuario;
import com.springBootone.service.UsuarioServico;


@Controller
public class HomeController {

	private Long idEdicao;
	
	@Autowired
	private UsuarioServico usuarioServico;
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	//1° forma de listar e retornar para tela
	@RequestMapping(value = "/listaUsuarios")
	public ModelAndView listaUsuarios(){
		ModelAndView modelAndView = new ModelAndView("listaUsuarios");
		modelAndView.addObject("listaUsuarios", usuarioServico.listaUsuarios());
		modelAndView.addObject("usuario", new Usuario());
		return modelAndView;
	}
	
	//2° forma de listar e retornar para tela
	@RequestMapping(value = "/listaUsuarios2")
	public String listaUsuarios2(Model model){
		model.addAttribute("listaUsuarios", usuarioServico.listaUsuarios());
		model.addAttribute("usuario", new Usuario());
		return "listaUsuarios";
	}
	
	//1° forma de salvar e retornar para tela de listagem, aqui recebe o objeto usuario instanciado no listaUsuarios()
	@RequestMapping(value = "/salvar", method = RequestMethod.POST)
	public ModelAndView salvar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes){
		Usuario usuarioAux;
		if(this.idEdicao != null){
			usuarioAux = new Usuario();
			usuarioAux.setId(this.idEdicao);
			usuarioAux.setNome(usuario.getNome());
			usuarioAux.setEmail(usuario.getEmail());
			usuarioServico.salvar(usuarioAux);
		}else{
			usuarioServico.salvar(usuario);			
		}
		return new ModelAndView("redirect:/listaUsuarios");
	}
	
	//2° forma de salvar e retornar para tela de listagem, aqui recebe os dados do form e insere em um novo usuario para salvar
	@RequestMapping(value = "/salvar2", method = RequestMethod.POST)
	public ModelAndView salvar2(@RequestParam("nome") String nome, @RequestParam("email") String email) {
		Usuario usuarioAux;
		if(this.idEdicao != null){
			usuarioAux = new Usuario();
			usuarioAux.setId(this.idEdicao);
			usuarioAux.setNome(nome);
			usuarioAux.setEmail(email);
			usuarioServico.salvar(usuarioAux);
		}else{
			Usuario usuario = new Usuario();
			usuario.setNome(nome);
			usuario.setEmail(email);
			usuarioServico.salvar(usuario);		
		}
		return new ModelAndView("redirect:/listaUsuarios");
	}
	
	@RequestMapping(value = "/excluir/{id}", method = RequestMethod.GET)
	public ModelAndView ecluir(@PathVariable("id") Usuario usuario){
		usuarioServico.excluir(usuario);
		return new ModelAndView("redirect:/listaUsuarios");
	}
	
	@RequestMapping(value = "/editar/{id}", method = RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") Usuario usuario){
		ModelAndView modelAndView = new ModelAndView("/listaUsuarios");
		this.idEdicao = usuario.getId();
		modelAndView.addObject("listaUsuarios", usuarioServico.listaUsuarios());
		modelAndView.addObject("usuario", usuario);
		return modelAndView;
	}

	public Long getIdEdicao() {
		return idEdicao;
	}

	public void setIdEdicao(Long idEdicao) {
		this.idEdicao = idEdicao;
	}
	

	
}
