package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import beans.Amenties;
import beans.AmentiesHelp;
import beans.Apartment;
import beans.CommentForApartment;
import beans.Reservation;
import beans.SearchFields;
import beans.TypeOfApartment;
import beans.User;

public class ApartmentDAO {
private Map<String, Apartment> apartments = new HashMap<>();
	
	
	public ApartmentDAO() {
		
	}
	
	public ApartmentDAO(String contextPath) {
		readApartments(contextPath);
	}
	
	public Collection<Apartment> findAll() {
		return apartments.values();
	}
	
	public boolean removeApartment(String contextPath, String idStr) {
		String realId = idStr.substring(12, idStr.length()-1);
		Apartment apartmentToRemove = apartments.get(realId);
		if (apartmentToRemove == null) {
			return false;
		} else {
			apartments.remove(Long.toString(apartmentToRemove.getId()));
			
			ObjectMapper mapper = new ObjectMapper();
			String path = contextPath + "/apartments.json";
			
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			try {
				mapper.writeValue(Paths.get(path).toFile(), apartments);
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return true;
		}
	}
	
	public Collection<Apartment> getAllActive() {
		Collection<Apartment> toReturn = new ArrayList<Apartment>();
		
		for (Apartment apartment: apartments.values()) {
			if (apartment.isStatus()) {
				toReturn.add(apartment);
			}
		}
		
		return toReturn;
	}
	
	public Collection<Apartment> searchApartments(SearchFields searchFields) {
		List<Apartment> apartmentsToReturn = new ArrayList<Apartment>();

		int flag = 0;
		int max = 7;

		for (Apartment apartment: apartments.values()) {
			flag = 0;
			max = 7;
			if (apartment.getLocation()!=null) {
				if (searchFields.getPlace().equals("")) {
					max--;
				} else {
					if (apartment.getLocation().getAddress().getPlace().equals(searchFields.getPlace())) {
						flag++;
					}
				}
			}

			if (searchFields.getPriceFrom()==0) {
				max--;
			} else {
				if (apartment.getPriceForOneNight()>=searchFields.getPriceFrom()) {
					flag++;
				}
			}

			if (searchFields.getPriceTo()==0) {
				max--;
			} else {
				if (apartment.getPriceForOneNight()<=searchFields.getPriceTo()) {
					flag++;
				}
			}

			if (searchFields.getNumberOfRoomsFrom()==0) {
				max--;
			} else {
				if (apartment.getNumberOfRooms()>=searchFields.getNumberOfRoomsFrom()) {
					flag++;
				}
			}

			if (searchFields.getNumberOfRoomsTo()==0) {
				max--;
			} else {
				if (apartment.getNumberOfRooms()<=searchFields.getNumberOfRoomsTo()) {
					flag++;
				}
			}

			if (searchFields.getNumberOfGuestsFrom()==0) {
				max--;
			} else {
				if (apartment.getNumberOfGuests()>=searchFields.getNumberOfGuestsFrom()) {
					flag++;
				}
			}

			if (searchFields.getNumberOfGuestsTo()==0) {
				max--;
			} else {
				if (apartment.getNumberOfGuests()<=searchFields.getNumberOfGuestsTo()) {
					flag++;
				}
			}

			if (flag==max) {
				apartmentsToReturn.add(apartment);
			}

		}


		return apartmentsToReturn;
	}
	
	public Collection<Apartment> searchActiveApartments(SearchFields searchFields) {
		List<Apartment> apartmentsToReturn = new ArrayList<Apartment>();

		int flag = 0;
		int max = 7;
		for (Apartment apartment: apartments.values()) {
			if (apartment.isStatus()) {
				flag = 0;
				max = 7;
				if (apartment.getLocation()!=null) {
					if (searchFields.getPlace().equals("")) {
						max--;
					} else {
						if (apartment.getLocation().getAddress().getPlace().equals(searchFields.getPlace())) {
							flag++;
						}
					}
				}

				if (searchFields.getPriceFrom()==0) {
					max--;
				} else {
					if (apartment.getPriceForOneNight()>=searchFields.getPriceFrom()) {
						flag++;
					}
				}

				if (searchFields.getPriceTo()==0) {
					max--;
				} else {
					if (apartment.getPriceForOneNight()<=searchFields.getPriceTo()) {
						flag++;
					}
				}

				if (searchFields.getNumberOfRoomsFrom()==0) {
					max--;
				} else {
					if (apartment.getNumberOfRooms()>=searchFields.getNumberOfRoomsFrom()) {
						flag++;
					}
				}

				if (searchFields.getNumberOfRoomsTo()==0) {
					max--;
				} else {
					if (apartment.getNumberOfRooms()<=searchFields.getNumberOfRoomsTo()) {
						flag++;
					}
				}

				if (searchFields.getNumberOfGuestsFrom()==0) {
					max--;
				} else {
					if (apartment.getNumberOfGuests()>=searchFields.getNumberOfGuestsFrom()) {
						flag++;
					}
				}

				if (searchFields.getNumberOfGuestsTo()==0) {
					max--;
				} else {
					if (apartment.getNumberOfGuests()<=searchFields.getNumberOfGuestsTo()) {
						flag++;

					}
				}

				if (flag==max) {
					apartmentsToReturn.add(apartment);
				}

			}
		}

		return apartmentsToReturn;
	}
	
	public Collection<Apartment> searchHostApartments(SearchFields searchFields, User host) {
		List<Apartment> apartmentsToReturn = new ArrayList<Apartment>();

		int flag = 0;
		int max = 7;

		for (Apartment apartment: apartments.values()) {
			if (apartment.getHost()!=null) {
				if (apartment.getHost().getUsername().equals(host.getUsername())) {
					flag = 0;
					max = 7;
					if (apartment.getLocation()!=null) {
						if (searchFields.getPlace().equals("")) {
							max--;
						} else {
							if (apartment.getLocation().getAddress().getPlace().equals(searchFields.getPlace())) {
								flag++;
							}
						}
					}

					if (searchFields.getPriceFrom()==0) {
						max--;
					} else {
						if (apartment.getPriceForOneNight()>=searchFields.getPriceFrom()) {
							flag++;
						}
					}

					if (searchFields.getPriceTo()==0) {
						max--;
					} else {
						if (apartment.getPriceForOneNight()<=searchFields.getPriceTo()) {
							flag++;
						}
					}

					if (searchFields.getNumberOfRoomsFrom()==0) {
						max--;
					} else {
						if (apartment.getNumberOfRooms()>=searchFields.getNumberOfRoomsFrom()) {
							flag++;
						}
					}

					if (searchFields.getNumberOfRoomsTo()==0) {
						max--;
					} else {
						if (apartment.getNumberOfRooms()<=searchFields.getNumberOfRoomsTo()) {
							flag++;
						}
					}

					if (searchFields.getNumberOfGuestsFrom()==0) {
						max--;
					} else {
						if (apartment.getNumberOfGuests()>=searchFields.getNumberOfGuestsFrom()) {
							flag++;
						}
					}

					if (searchFields.getNumberOfGuestsTo()==0) {
						max--;
					} else {
						if (apartment.getNumberOfGuests()<=searchFields.getNumberOfGuestsTo()) {
							flag++;
						}
					}

					if (flag==max) {
						apartmentsToReturn.add(apartment);
					}
				}

			}
		}


		return apartmentsToReturn;
	}
	
	
	public Collection<Apartment> getHostActive(User host) {
		Collection<Apartment> toReturn = new ArrayList<Apartment>();
		for (Apartment apartment: apartments.values()) {
			if (apartment.getHost()!=null) {
				if (apartment.getHost().getUsername().equals(host.getUsername())) {
					toReturn.add(apartment);
				}		
			}
		}
		return toReturn;
		
		//SAMO ZA AKTIVNE
//		for (Apartment apartment: apartments.values()) {
//			if (apartment.getHost()!=null) {
//				if (apartment.isStatus() && apartment.getHost().getUsername().equals(host.getUsername())) {
//					toReturn.add(apartment);
//				}		
//			}
//		}
//		return toReturn;
	}
	
	public Apartment save(Apartment apartment) {
		apartments.put(Long.toString(apartment.getId()), apartment);
		return apartment;
	}
	
	public Apartment editApartment(String contextPath, Apartment apartment) {
		Apartment apartmentToSave = apartments.get(Long.toString(apartment.getId()));
		
		apartments.remove(Long.toString(apartment.getId()));
		
		apartmentToSave.setType(apartment.getType());
		apartmentToSave.setNumberOfRooms(apartment.getNumberOfRooms());
		apartmentToSave.setNumberOfGuests(apartment.getNumberOfGuests());
		apartmentToSave.setLocation(apartment.getLocation());
		apartmentToSave.setPriceForOneNight(apartment.getPriceForOneNight());
		apartmentToSave.setCheckInTime(apartment.getCheckInTime());
		apartmentToSave.setCheckOutTime(apartment.getCheckOutTime());
		apartmentToSave.setStatus(apartment.isStatus());
		if (!apartment.getImages().isEmpty()) {
			apartmentToSave.setImages(apartment.getImages());
		}
		
		
		
		return printApartments(contextPath, apartmentToSave);
	}
	
	public void editUserInApartment(String contextPath, User user) {
		int flag = 0;
		List<Long>idsToDelete = new ArrayList<Long>();
		List<Apartment> apartmentsToAdd = new ArrayList<Apartment>();
		
		for (Apartment apartment: apartments.values()) {
			flag = 0;
			if (apartment==null) {
				System.out.println("greska");
			} else {
			if (apartment.getHost()!=null) {
				if (apartment.getHost().getUsername().equals(user.getUsername())) {
					flag = 1;
					apartment.setHost(user);
					idsToDelete.add(apartment.getId());
				}
			}
			
			if (apartment.getComments()!=null) {
				for (CommentForApartment comment: apartment.getComments()) {
					if (comment.getGuest()!=null) {
						if (comment.getGuest().getUsername().equals(user.getUsername())) {
							comment.setGuest(user);
							if (flag == 0) {
								flag = 1;
								idsToDelete.add(apartment.getId());
							}
						}
					}
				}
			}
			
			if (apartment.getReservations() != null) {
				for (Reservation reservation: apartment.getReservations()) {
					if (reservation.getGuest() != null) {
						if (reservation.getGuest().getUsername().equals(user.getUsername())) {
							reservation.setGuest(user);
							if (flag == 0) {
								flag = 1;
								idsToDelete.add(apartment.getId());
							}
						}
					}
				}
			}
			
			if (flag == 1) {
				apartmentsToAdd.add(apartment);
			}
			}
		}
		
		for (Long id: idsToDelete) {
			apartments.remove(Long.toString(id));
		}
		
		for (Apartment apartment: apartmentsToAdd) {
			printApartments(contextPath, apartment);
		}
		
		

	}
	
	public void editCommentInApartment(String contextPath, CommentForApartment comment) {
		Apartment ap = null;
		for(Apartment a: apartments.values()) {
			if(a.getId() == comment.getApartment().getId()) {
				a.getComments().add(comment);
				ap = a;
				}
			}
		if(ap != null) {
			apartments.remove(Long.toString(ap.getId()));
			apartments.put(Long.toString(ap.getId()), ap);
		}
		
	}
	
	public Apartment findApartment(long id) {	
		return apartments.get(Long.toString(id));
	}
	public Apartment findOne(Apartment apartment) {	
		return apartments.get(Long.toString(apartment.getId()));
	}
	
	private void loadApartments(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/apartments.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					String idString = st.nextToken().trim();
					String type = st.nextToken().trim();
					String numberOfRoomsString = st.nextToken().trim();
					String numberOfGuestsString = st.nextToken().trim();
					//String longitudeString = st.nextToken().trim();
					//String latitudeString = st.nextToken().trim();
					//String streetAndNumber = st.nextToken().trim();
					//String place = st.nextToken().trim();
					//String postcodeString = st.nextToken().trim();
					String priceForOneNightString = st.nextToken().trim();
					String statusString = st.nextToken().trim();
					
					long id = Long.parseLong(idString);
					int numberOfRooms = Integer.parseInt(numberOfRoomsString);
					int numberOfGuests = Integer.parseInt(numberOfGuestsString);
					//double longitude = Double.parseDouble(longitudeString);
					//double latitude = Double.parseDouble(latitudeString);
					//long postcode = Long.parseLong(postcodeString);
					double priceForOneNight = Double.parseDouble(priceForOneNightString);
					boolean status = Boolean.parseBoolean(statusString);
					
					//Address address = new Address(streetAndNumber, place, postcode);
					
					//Location location = new Location(longitude, latitude, address);
					
					apartments.put(idString, new Apartment(id, TypeOfApartment.ROOM, numberOfRooms, numberOfGuests, priceForOneNight, status));
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}
	
	public Apartment printApartments(String contextPath, Apartment apartment) {
		ObjectMapper mapper = new ObjectMapper();
		DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
		mapper.setDateFormat(DATE_FORMAT);
		String path = contextPath + "/apartments.json";
		apartments.put(Long.toString(apartment.getId()), apartment);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			mapper.writeValue(Paths.get(path).toFile(), apartments);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return apartment;
		
	}
	
	public void readApartments(String contextPath) {
		ObjectMapper mapper = new ObjectMapper();
		
		//za datum
		DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
		mapper.setDateFormat(DATE_FORMAT);
		
		BufferedReader in = null;
		
		try {
			
			File file = new File(contextPath + "/apartments.json");
			in = new BufferedReader(new FileReader(file));
			
			Map<String, Apartment> apartmentsMap = mapper.readValue(in, new TypeReference<Map<String, Apartment>>() {
            });
			
			apartments = apartmentsMap;
			 

		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public Apartment saveAmenitiesToApartment(Apartment apartment, AmentiesHelp amenitiesHelp, String contextPath) {
		
		apartments.remove(Long.toString(apartment.getId()));
		
		List<Amenties> amentiesToAdd = new ArrayList<Amenties>();
		
		long id = 0;
		
		for (String amentiesString: amenitiesHelp.getAmenities()) {
			Amenties amenties = new Amenties(id, amentiesString);
			id++;
			amentiesToAdd.add(amenties);
			
		}
		
		apartment.setListOfAmenities(amentiesToAdd);
		
		printApartments(contextPath, apartment);
		
		return apartment;
	}

	public Apartment addDatesToApartment(String contextPath, Reservation reservation) {
		Apartment apartment = apartments.get(Long.toString(reservation.getApartment().getId()));
		
		apartments.remove(Long.toString(apartment.getId()));
			
		List<Date> pickedDates = addDays(reservation.getStartDate(), reservation.getNumberOfOvernights());
		
		for (Date oneDate : pickedDates) {
			apartment.getReleaseDates().add(oneDate);
		}
		
		return printApartments(contextPath, apartment);
		
	}
	
	private List<Date> addDays(Date startDate, int days) {
		
		List<Date> datesToReturn = new ArrayList<Date>();
		
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        String dateString = DATE_FORMAT.format(startDate);
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c = Calendar.getInstance();
		
	    try {
	    	
			c.setTime(sdf.parse(dateString));
			
		} catch (java.text.ParseException e) {

			e.printStackTrace();
		}
	    
	    c.add(Calendar.DAY_OF_MONTH, 0);
    	
    	String newDate2 = sdf.format(c.getTime());
    	
    	Date returnDate2 = new Date();
		try {
			returnDate2 = DATE_FORMAT.parse(newDate2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		datesToReturn.add(returnDate2);
	    
		
	    for (int i = 0; i<days-1;i++) {
	    	c.add(Calendar.DAY_OF_MONTH, 1);
	    	
	    	String newDate = sdf.format(c.getTime());
	    	
	    	Date returnDate = new Date();
			try {
				returnDate = DATE_FORMAT.parse(newDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			datesToReturn.add(returnDate);
			
	    }
	    
		
		return datesToReturn;
	}

	public Collection<String> getAllDatesForApartment(Apartment apartment) {
		List<String> datesToReturn = new ArrayList<String>();
		
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
        
		
		if (apartment!=null) {
			
			Apartment foundApartment = apartments.get(Long.toString(apartment.getId()));
			if (foundApartment!=null) {
				if (foundApartment.getReleaseDates()!=null) {
					for (Date date: foundApartment.getReleaseDates()) {
						if (date!=null) {
							String dateString = DATE_FORMAT.format(date);
							datesToReturn.add(dateString);
						}
					}
				}
			}
		}
		
		return datesToReturn;
	}

	public boolean whithdrawalReservation(String contextPath, Reservation reservation) {
		
		Apartment apartment = apartments.get(Long.toString(reservation.getApartment().getId()));
		
		apartments.remove(Long.toString(apartment.getId()));
			
		List<Date> pickedDates = addDays(reservation.getStartDate(), reservation.getNumberOfOvernights());
		
		for (Date oneDate : pickedDates) {
			apartment.getReleaseDates().remove(oneDate);
		}
		
		printApartments(contextPath, apartment);
	    
		
		return true;
	}

	public boolean rejectReservation(String contextPath, Reservation reservation) {
		Apartment apartment = apartments.get(Long.toString(reservation.getApartment().getId()));
		
		apartments.remove(Long.toString(apartment.getId()));
			
		List<Date> pickedDates = addDays(reservation.getStartDate(), reservation.getNumberOfOvernights());
		
		for (Date oneDate : pickedDates) {
			apartment.getReleaseDates().remove(oneDate);
		}
		
		printApartments(contextPath, apartment);
	    
		
		return true;
	}

	public boolean deleteAmenitie(String contextPath, Amenties amenitie) {
		List<Apartment> apartmentsToDelete = new ArrayList<Apartment>();
		List<Apartment> apartmentsToAdd = new ArrayList<Apartment>();
		for (Apartment apartment: apartments.values()) {
			apartmentsToDelete.add(apartment);
			if (apartment.getListOfAmenities()!=null) {
				for (Amenties oneAmenitie: apartment.getListOfAmenities()) {
					if (oneAmenitie.getName().equals(amenitie.getName())) {
						apartment.getListOfAmenities().remove(oneAmenitie);
						break;
					}
				}
			}
			
			apartmentsToAdd.add(apartment);
			
		}
		
		for (Apartment apartmentToDelete: apartmentsToDelete) {
			apartments.remove(Long.toString(apartmentToDelete.getId()));
		}
		
		for (Apartment apartmentToAdd: apartmentsToAdd) {
			printApartments(contextPath, apartmentToAdd);
		}
		
		return true;
	}

	public boolean editAmenitie(String contextPath, Amenties oldAmenitie, Amenties amenitie) {
		List<Apartment> apartmentsToDelete = new ArrayList<Apartment>();
		List<Apartment> apartmentsToAdd = new ArrayList<Apartment>();
		for (Apartment apartment: apartments.values()) {
			apartmentsToDelete.add(apartment);
			if (apartment.getListOfAmenities()!=null) {
				for (Amenties oneAmenitie: apartment.getListOfAmenities()) {
					if (oneAmenitie.getName().equals(oldAmenitie.getName())) {
						apartment.getListOfAmenities().remove(oneAmenitie);
						apartment.getListOfAmenities().add(amenitie);
						break;
					}
				}
			}
			
			apartmentsToAdd.add(apartment);
			
		}
		
		for (Apartment apartmentToDelete: apartmentsToDelete) {
			apartments.remove(Long.toString(apartmentToDelete.getId()));
		}
		
		for (Apartment apartmentToAdd: apartmentsToAdd) {
			printApartments(contextPath, apartmentToAdd);
		}
		
		return true;
	}

	public Collection<Apartment> filterActiveApartmentsType(Apartment sentApartment) {
		List<Apartment> apartmentsToReturn = new ArrayList<Apartment>();
		for (Apartment apartment: apartments.values()) {
			if (apartment.isStatus()) {
				if (apartment.getType().equals(sentApartment.getType())) {
					apartmentsToReturn.add(apartment);
				}
			}
		}
		
		return apartmentsToReturn;
	}

	public Collection<Apartment> filterApartmentsType(Apartment sentApartment) {
		List<Apartment> apartmentsToReturn = new ArrayList<Apartment>();
		for (Apartment apartment: apartments.values()) {
			if (apartment.getType().equals(sentApartment.getType())) {
				apartmentsToReturn.add(apartment);
			}
		}
		
		return apartmentsToReturn;
	}

	public Collection<Apartment> filterApartmentsStatus(Apartment sentApartment) {
		List<Apartment> apartmentsToReturn = new ArrayList<Apartment>();
		for (Apartment apartment: apartments.values()) {
			if (apartment.isStatus()==sentApartment.isStatus()) {
				apartmentsToReturn.add(apartment);
			}
		}
		
		return apartmentsToReturn;
	}

	public Collection<Apartment> filterHostApartmentsStatus(Apartment sentApartment, User host) {
		Collection<Apartment> toReturn = new ArrayList<Apartment>();
		for (Apartment apartment: apartments.values()) {
			if (apartment.getHost()!=null) {
				if (apartment.getHost().getUsername().equals(host.getUsername())) {
					if (apartment.isStatus()==sentApartment.isStatus()) {
						toReturn.add(apartment);
					}
				}		
			}
		}
		return toReturn;
	}

	public Collection<Apartment> filterHostApartmentsType(Apartment sentApartment, User host) {
		Collection<Apartment> toReturn = new ArrayList<Apartment>();
		for (Apartment apartment: apartments.values()) {
			if (apartment.getHost()!=null) {
				if (apartment.getHost().getUsername().equals(host.getUsername())) {
					if (apartment.getType().equals(sentApartment.getType())) {
						toReturn.add(apartment);
					}
				}		
			}
		}
		return toReturn;
	}
	
		
	
	
	
}
