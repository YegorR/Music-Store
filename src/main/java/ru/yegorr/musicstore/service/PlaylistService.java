package ru.yegorr.musicstore.service;

import org.springframework.web.multipart.MultipartFile;
import ru.yegorr.musicstore.dto.request.IdDto;
import ru.yegorr.musicstore.dto.response.PlaylistBriefDto;
import ru.yegorr.musicstore.dto.response.PlaylistResponseDto;
import ru.yegorr.musicstore.exception.ApplicationException;

import java.util.List;

public interface PlaylistService {
    PlaylistResponseDto getPlaylist(Long userId, Long playlistId) throws ApplicationException;

    PlaylistResponseDto createPlaylist(Long userId, String name) throws ApplicationException;

    PlaylistResponseDto changePlaylist(Long userId, Long playlistId, String name, List<IdDto> tracks) throws ApplicationException;

    void deletePlaylist(Long userId, Long playlistId) throws ApplicationException;

    void unloadPlaylistImage(Long userId, Long playlistId, MultipartFile image) throws ApplicationException;

    byte[] getPlaylistImage(Long playlistId) throws ApplicationException;

    List<PlaylistBriefDto> searchPlaylists(String query) throws ApplicationException;

    List<PlaylistBriefDto> getPlaylistsOfUser(Long userId) throws ApplicationException;
}
