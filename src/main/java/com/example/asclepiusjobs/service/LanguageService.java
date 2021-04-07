package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.dto.LanguageDto;
import com.example.asclepiusjobs.model.Cv;
import com.example.asclepiusjobs.model.Language;
import com.example.asclepiusjobs.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    public LanguageDto convertLanguageEntityToDto(Language languageEntity){
        LanguageDto languageDto=new LanguageDto();
        languageDto.setName(languageEntity.getName());
        languageDto.setLevel(languageEntity.getLevel());
        return languageDto;
    }

    public Language createLanguage(Cv cv, LanguageDto languageDto){
        Language language=new Language();
        language.setCv(cv);
        language.setName(languageDto.getName());
        language.setLevel(languageDto.getLevel());
        return languageRepository.save(language);
    }

    public Language getById(Long id) throws Exception {
        Optional<Language> optionalLanguage=languageRepository.findById(id);
        if(optionalLanguage.isPresent()){
            return optionalLanguage.get();
        }else {
            throw new Exception("Language not found");
        }
    }

    public void deleteById(Long id){
        languageRepository.deleteById(id);
    }

}
