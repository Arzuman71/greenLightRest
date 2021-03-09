package am.greenlight.greenlight.mapper;


public interface RootMapper<E,Req,Resp> {

    E toEntity(Req request);

    Resp toResponse(E entity);
}
