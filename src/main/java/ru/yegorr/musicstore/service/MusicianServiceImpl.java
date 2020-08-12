package ru.yegorr.musicstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.response.AlbumDescriptionDto;
import ru.yegorr.musicstore.dto.response.BriefMusicianDto;
import ru.yegorr.musicstore.dto.response.MusicianDto;
import ru.yegorr.musicstore.dto.response.MusicianLetterDto;
import ru.yegorr.musicstore.entity.AlbumEntity;
import ru.yegorr.musicstore.entity.MusicianEntity;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.exception.ServerException;
import ru.yegorr.musicstore.repository.MusicianAbcCount;
import ru.yegorr.musicstore.repository.MusicianRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MusicianServiceImpl implements MusicianService {

  private final MusicianRepository musicianRepository;

  @Autowired
  public MusicianServiceImpl(MusicianRepository musicianRepository) {
    this.musicianRepository = musicianRepository;
  }

  @Transactional
  @Override
  public MusicianDto createMusician(String name, String description) {
    MusicianEntity musician = new MusicianEntity();
    musician.setName(name);
    musician.setDescription(description);
    musician = musicianRepository.save(musician);

    return translateEntityToDto(musician);
  }

  @Transactional
  @Override
  public MusicianDto changeMusician(Long id, String name, String description)
          throws ClientException {
    MusicianEntity musician = musicianRepository.findById(id).
            orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND,
                    "The musician is not exists"));

    musician.setName(name);
    musician.setDescription(description);
    musician.setMusicianId(id);

    musician = musicianRepository.save(musician);
    return translateEntityToDto(musician);
  }

  @Transactional
  @Override
  public void deleteMusician(Long id) throws ClientException {
    if (!musicianRepository.existsById(id)) {
      throw new ClientException(HttpStatus.NOT_FOUND, "The musician is not exists");
    }
    musicianRepository.deleteById(id);
  }

  @Transactional
  @Override
  public MusicianDto getMusician(Long id) throws ClientException {
    Optional<MusicianEntity> musician = musicianRepository.findById(id);
    if (musician.isEmpty()) {
      throw new ClientException(HttpStatus.NOT_FOUND, "The musician is not exists");
    }
    return translateEntityToDto(musician.get());
  }

  @Override
  @Transactional
  public Map<String, Integer> getMusicianCountByAbc() {
    List<MusicianAbcCount> counts = musicianRepository.getMusicianCountByAbc();
    Map<String, Integer> result = new LinkedHashMap<>();
    for (MusicianAbcCount count : counts) {
      result.put(count.getLetter(), count.getCount());
    }
    return result;
  }

  private MusicianDto translateEntityToDto(MusicianEntity entity) {
    MusicianDto musicianDto = new MusicianDto();
    musicianDto.setId(entity.getMusicianId());
    musicianDto.setDescription(entity.getDescription());
    musicianDto.setName(entity.getName());

    List<AlbumEntity> albumsAndSingles = entity.getAlbums();

    if (albumsAndSingles == null) {
      return musicianDto;
    }

    List<AlbumDescriptionDto> albums = new ArrayList<>();
    List<AlbumDescriptionDto> singles = new ArrayList<>();

    for (AlbumEntity release : albumsAndSingles) {
      AlbumDescriptionDto releaseDto = new AlbumDescriptionDto();
      releaseDto.setId(release.getAlbumId());
      releaseDto.setName(release.getName());
      releaseDto.setReleaseDate(release.getReleaseDate());
      releaseDto.setSingle(release.getSingle());

      if (release.getSingle()) {
        singles.add(releaseDto);
      } else {
        albums.add(releaseDto);
      }
    }

    musicianDto.setSingles(singles);
    musicianDto.setAlbums(albums);

    return musicianDto;
  }

  @Override
  @Transactional
  public List<MusicianLetterDto> getMusiciansByLetter(String letter) {
    return musicianRepository.findAllByNameStartingWithIgnoreCase(letter).stream().
            map((entity) -> {
              MusicianLetterDto dto = new MusicianLetterDto();
              dto.setId(entity.getMusicianId());
              dto.setName(entity.getName());
              return dto;
            }).collect(Collectors.toList());
  }

  @Override
  @Transactional
  public void saveImage(Long musicianId, MultipartFile image) throws ServerException {
    try {
      musicianRepository.findById(musicianId).
              orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND,
                      "The musician is not exists")).setImage(image.getBytes());
    } catch (IOException ex) {
      throw new ServerException(ex);
    }
  }

  @Override
  @Transactional
  public byte[] getImage(Long musicianId) throws ClientException {
    return musicianRepository.findById(musicianId).
            orElseThrow(() -> new ClientException(HttpStatus.NOT_FOUND,
                    "The musician is not exists")).getImage();
  }

  @Override
  @Transactional
  public List<BriefMusicianDto> searchMusicians(String query) {
    return musicianRepository.findAllByNameContainingIgnoreCase(query).stream().
            map((entity) -> {
              BriefMusicianDto dto = new BriefMusicianDto();
              dto.setName(entity.getName());
              dto.setId(entity.getMusicianId());
              return dto;
            }).collect(Collectors.toList());
  }
}
