package com.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.IntPredicate;

import org.springframework.stereotype.Repository;

import com.model.Person;

@Repository("fakeDAO")
public class FakePersonDataAccessService implements PersonDAO{
	private static List<Person> DB = new ArrayList<>();
	@Override
	public int insertPerson(UUID id, Person person) {
		DB.add(new Person(id, person.getName()));
		return 1;
	}
	
	@Override
	public List<Person> selectAllPeople() {
		return DB;
	}

	@Override
	public Optional<Person> selectPersonById(UUID id) {
		// TODO Auto-generated method stub
		return DB.stream()
				.filter(person -> person.getId().equals(id))
				.findFirst();
	}

	@Override
	public int deletePersonById(UUID id) {
		Optional<Person> personMayBe = selectPersonById(id);
		if(personMayBe.isPresent()) {
			DB.remove(personMayBe.get());
			return 0;
		} else return 1;
			
	}

	@Override
	public int updatePersonById(UUID id, Person update) {
		// TODO Auto-generated method stub
		return selectPersonById(id).map( person -> {
			int indexOfPersonToDelete = DB.indexOf(person);
			if(indexOfPersonToDelete >= 0) {
				DB.set(indexOfPersonToDelete, new Person(id, update.getName()));
				return 1;
			} return 0;
		}).orElse(0);
	}

	

}
