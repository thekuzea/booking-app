package com.cdvtech.talibanairlines.controller

import com.cdvtech.talibanairlines.model.entity.Profile
import com.cdvtech.talibanairlines.service.ProfileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/profiles")
class ProfileController {

    @Autowired
    ProfileService profileService

    @GetMapping("{username}")
    Profile getOne(@PathVariable("username") String username) {
        return profileService.getByUsername(username)
    }

    @GetMapping("")
    List<Profile> getAll() {
        return profileService.getAll()
    }

    @PostMapping("/registration")
    ResponseEntity<Profile> addProfile(@RequestBody Profile profile) {
        if (profileService.save(profile) != null) {
            return new ResponseEntity<>(HttpStatus.CREATED)
        }

        return new ResponseEntity<>(new String("Unable to save profile. It already exists."), HttpStatus.CONFLICT)
    }

    @PutMapping(value = "{username}")
    ResponseEntity<?> updateProfile(@PathVariable("username") String username, @RequestBody Profile profile) {
        Profile currentProfile = profileService.update(username, profile)

        if (currentProfile == null) {
            return new ResponseEntity(new String("Unable to update profile with username " + username + ". It's not found."),
                    HttpStatus.NOT_FOUND)
        }

        return new ResponseEntity<Profile>(currentProfile, HttpStatus.OK)
    }

    @DeleteMapping(value = "{username}")
    ResponseEntity<?> deleteClient(@PathVariable("username") String username) {
        Profile currentProfile = profileService.delete(username)

        if (currentProfile == null) {
            return new ResponseEntity(new String("Unable to delete profile with username " + username + ". It's not found."),
                    HttpStatus.NOT_FOUND)
        }

        return new ResponseEntity<>(HttpStatus.OK)
    }
}
