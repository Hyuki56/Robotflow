package mbc.aiseat.controller.hyuk;

import mbc.aiseat.entity.hyuk.reserve.Library;
import mbc.aiseat.repository.hyuk.reserve.LibraryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LibraryController {

    private final LibraryRepository libraryRepository;

    public LibraryController(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    @GetMapping("/libraries")
    public List<Library> getLibraries() {
        return libraryRepository.findAll();
    }
}
