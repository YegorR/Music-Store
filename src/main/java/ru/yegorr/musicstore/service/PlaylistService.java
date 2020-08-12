package ru.yegorr.musicstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.IdDto;
import ru.yegorr.musicstore.dto.response.BriefPlaylistDto;
import ru.yegorr.musicstore.dto.response.PlaylistDto;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.exception.ServerException;

import java.util.List;

public interface PlaylistService {
  PlaylistDto getPlaylist(Long userId, Long playlistId) throws ClientException;

  PlaylistDto createPlaylist(Long userId, String name) throws ClientException;

  PlaylistDto changePlaylist(Long userId, Long playlistId, String name, List<IdDto> tracks)
          throws ClientException;

  void deletePlaylist(Long userId, Long playlistId) throws ClientException;

  void unloadPlaylistImage(Long userId, Long playlistId, MultipartFile image)
          throws ServerException;

  byte[] getPlaylistImage(Long playlistId) throws ClientException;

  List<BriefPlaylistDto> searchPlaylists(String query) throws ClientException;

  List<BriefPlaylistDto> getPlaylistsOfUser(Long userId) throws ClientException;
}
