package CourseWork.Core.CourseWork.Application.UseCases.UserUseCases;

import CourseWork.Core.CourseWork.Application.UseCases.UserInterfaces.ISubmitRequestUseCase;
import CourseWork.Core.CourseWork.Domain.Interfaces.IRequestRepository;
import CourseWork.Core.CourseWork.Domain.Request;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class SubmitRequestUseCase implements ISubmitRequestUseCase {
    private final IRequestRepository requestRepository;

    public SubmitRequestUseCase(IRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public boolean submit(String description, UUID userId) {
        boolean alreadyExists = requestRepository.getAll().stream()
                .anyMatch(r -> r.UserId.equals(userId) && r.Description.equals(description));

        if (alreadyExists) {
            return false;
        }

        Request request = new Request(UUID.randomUUID(), description, false, userId);
        return requestRepository.add(request);
    }

    @Override
    public List<Request> getRequestsByUser(UUID userId) {
        return requestRepository.getAll()
                .stream()
                .filter(r -> r.UserId.equals(userId))
                .collect(Collectors.toList());
    }
}
