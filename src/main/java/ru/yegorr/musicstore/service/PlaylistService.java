package ru.yegorr.musicstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.IdDto;
import ru.yegorr.musicstore.dto.response.PlaylistBriefDto;
import ru.yegorr.musicstore.dto.response.PlaylistResponseDto;
import ru.yegorr.musicstore.exception.ClientException;
import ru.yegorr.musicstore.exception.ServerException;

import java.util.List;

public interface PlaylistService {
    PlaylistResponseDto getPlaylist(Long userId, Long playlistId) throws ClientException;

    PlaylistResponseDto createPlaylist(Long userId, String name) throws ClientException;

    PlaylistResponseDto changePlaylist(Long userId, Long playlistId, String name, List<IdDto> tracks) throws ClientException;

    void deletePlaylist(Long userId, Long playlistId) throws ClientException;

    void unloadPlaylistImage(Long userId, Long playlistId, MultipartFile image) throws ClientException, ServerException;

    byte[] getPlaylistImage(Long playlistId) throws ClientException;

    List<PlaylistBriefDto> searchPlaylists(String query) throws ClientException;

    List<PlaylistBriefDto> getPlaylistsOfUser(Long userId) throws ClientException;
}
