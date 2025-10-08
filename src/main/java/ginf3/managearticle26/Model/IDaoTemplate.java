package ginf3.managearticle26.Model;

import java.util.List;

public interface IDaoTemplate<T> {
     List<T> findAll() ;
     T findByCode(String code) ;
     boolean add(T object) ;
     boolean update(T object);
     boolean delete(String code);
}
