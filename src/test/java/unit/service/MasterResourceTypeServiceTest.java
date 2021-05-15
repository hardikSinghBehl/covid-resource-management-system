package unit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hardik.pomfrey.entity.MasterResourceType;
import com.hardik.pomfrey.repository.MasterResourceTypeRepository;
import com.hardik.pomfrey.service.MasterResourceTypeService;

class MasterResourceTypeServiceTest {

	private MasterResourceTypeRepository masterResourceTypeRepository;
	private MasterResourceTypeService masterResourceTypeService;

	@BeforeEach
	void setUp() {
		masterResourceTypeRepository = mock(MasterResourceTypeRepository.class);
		masterResourceTypeService = new MasterResourceTypeService(masterResourceTypeRepository);
	}

	@Test
	void retreive_whenCalled_returnsListOfMasterResourceTypes() {
		when(masterResourceTypeRepository.findAll()).thenReturn(List.of(new MasterResourceType(),
				new MasterResourceType(), new MasterResourceType(), new MasterResourceType()));

		final var response = masterResourceTypeService.retreive();

		assertNotNull(response.getBody());
		assertEquals(4, response.getBody().size());
		assertThat(response.getBody().get(0)).isInstanceOf(MasterResourceType.class);
		verify(masterResourceTypeRepository).findAll();
	}

}
