package com.example.booking.domain.service;

import com.example.booking.domain.dto.BlockDto;
import com.example.booking.domain.dto.CreateBlockDto;
import com.example.booking.domain.dto.UpdateBlockDto;
import com.example.booking.domain.exception.ResourceNotFoundException;
import com.example.booking.domain.model.Block;
import com.example.booking.domain.model.Property;
import com.example.booking.domain.repository.BlockRepository;
import com.example.booking.domain.repository.PropertyRepository;
import com.example.booking.domain.validator.BlockValidator;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BlockService {

    private BlockRepository blockRepository;
    private PropertyRepository propertyRepository;
    private BlockValidator blockValidator;

    @Transactional
    public BlockDto create(CreateBlockDto createBlockDto) {
        if (!propertyRepository.existsById(createBlockDto.getPropertyId())) {
            throw new ResourceNotFoundException("Property not found");
        }

        Property property = propertyRepository.findById(createBlockDto.getPropertyId()).get();

        Block block = Block.builder()
                .property(property)
                .startDate(createBlockDto.getStart())
                .endDate(createBlockDto.getEnd())
                .build();

        blockValidator.validateCreateOrUpdate(block);

        block = blockRepository.save(block);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(block, BlockDto.class);
    }

    @Transactional
    public BlockDto update(Long id, UpdateBlockDto updateBlockDto) {
        if (!blockRepository.existsById(id)) {
            throw new ResourceNotFoundException("Block not found");
        }

        Block block = blockRepository.findById(id).get();

        Block updatedBlock = Block.builder()
                .startDate(updateBlockDto.getStart())
                .endDate(updateBlockDto.getEnd())
                .build();

        block.mergePropertiesFrom(updatedBlock);
        blockValidator.validateCreateOrUpdate(block);

        block = blockRepository.save(block);

        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(block, BlockDto.class);
    }

    public BlockDto find(Long id) {
        if (!blockRepository.existsById(id)) {
            throw new ResourceNotFoundException("Block not found");
        }

        Block block = blockRepository.findById(id).get();
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(block, BlockDto.class);
    }

    public List<BlockDto> listAll() {
        List<Block> blocks = blockRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();

        List<BlockDto> blockDtos = blocks.stream()
                .map(block -> modelMapper.map(block, BlockDto.class))
                .collect(Collectors.toList());

        return blockDtos;
    }

    @Transactional
    public void delete(Long id) {
        if (!blockRepository.existsById(id)) {
            throw new ResourceNotFoundException("Block not found");
        }

        blockRepository.deleteById(id);
    }
}
