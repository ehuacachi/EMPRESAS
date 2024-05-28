package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.service.CategoriaService;
import com.okcompu.ecommerce.backendokcompu.model.Categoria;
import com.okcompu.ecommerce.backendokcompu.repo.CategoriaRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends CRUDImpl<Categoria, Long> implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    @Override
    protected GenericRepo<Categoria, Long> getRepo() {
        return categoriaRepository;
    }

//    @Override
//    public List<Categoria> findAll() {
//        return categoriaRepository.findAll();
//    }
//
//    @Override
//    public Page<Categoria> findAll(Pageable pageable) {
//        return categoriaRepository.findAll(pageable);
//    }
//
//    @Override
//    public Categoria findById(Long id) {
//        return categoriaRepository.findById(id).orElse(new Categoria());
//    }
//
//    @Override
//    public Categoria create(Categoria categoria) {
//
//        return categoriaRepository.save(categoria);
//    }
//
//    @Override
//    public Categoria update(Long id, Categoria categoria) {
//        categoria.setIdCategoria(id);
//        return categoriaRepository.save(categoria);
//    }
//
//    @Override
//    public void delete(Long id) {
//        categoriaRepository.deleteById(id);
//    }
}
