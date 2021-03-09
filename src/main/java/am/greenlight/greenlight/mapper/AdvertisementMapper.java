package am.greenlight.greenlight.mapper;

import am.greenlight.greenlight.dto.request.AdvertisementRequest;
import am.greenlight.greenlight.dto.response.AdvertisementResponse;
import am.greenlight.greenlight.model.Advertisement;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AdvertisementMapper implements RootMapper<Advertisement, AdvertisementRequest, AdvertisementResponse> {

    @Override
    public Advertisement toEntity(AdvertisementRequest request) {
        return new ModelMapper().map(request, Advertisement.class);
    }

    @Override
    public AdvertisementResponse toResponse(Advertisement entity) {
        return new ModelMapper().map(entity, AdvertisementResponse.class);
    }
}
