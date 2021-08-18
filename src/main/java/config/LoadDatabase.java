package config;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import entities.Category;
import entities.Movie;
import entities.Reservation;
import entities.Role;
import entities.Room;
import entities.Screening;
import entities.Seat;
import entities.SeatReserved;
import entities.User;
import repositories.CategoryRepository;
import repositories.MovieRepository;
import repositories.ReservationRepository;
import repositories.RoleRepository;
import repositories.RoomRepository;
import repositories.ScreeningRepository;
import repositories.SeatRepository;
import repositories.SeatReservedRepository;
import repositories.UserRepository;

@Configuration
public class LoadDatabase {

	private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
	
	@Bean
	CommandLineRunner initDatabase(RoomRepository repositoryR, SeatRepository repositoryS,
			CategoryRepository repositoryC, RoleRepository repositoryRo, UserRepository repositoryU,
			MovieRepository repositoryM, ScreeningRepository repositorySc, ReservationRepository reservationRep, 
			SeatReservedRepository reservedRep, BCryptPasswordEncoder passwordEncoder) {
		return args -> {
			List<Role> roles = repositoryRo.findAll();
			if(roles.size() == 0) {
				log.info("Preloading " + repositoryRo.save(new Role("ROLE_USER")).toString());
				log.info("Preloading " + repositoryRo.save(new Role("ROLE_ADMIN")).toString());
			}
			else log.info("Roles already added");
			
			List<User> users = repositoryU.findAll();
			if(users.size() == 0) {
				User user = new User("Admin", "Cinema", "cinema@gmail.com", passwordEncoder.encode("admin"),
						new ArrayList<Role>());
				user.getRoles().add(repositoryRo.findByName("ROLE_ADMIN"));
				log.info("Preloading " + repositoryU.save(user).toString());
				
				user = new User("Adam", "Barada", "adam1barada@gmail.com", passwordEncoder.encode("adambarada"),
						new ArrayList<Role>());
				user.getRoles().add(repositoryRo.findByName("ROLE_USER"));
				log.info("Preloading " + repositoryU.save(user).toString());
				
				user = new User("Vanessa", "Khourieh", "vanessakhourieh@gmail.com", passwordEncoder.encode("vanessakhourieh"),
						new ArrayList<Role>());
				user.getRoles().add(repositoryRo.findByName("ROLE_USER"));
				log.info("Preloading " + repositoryU.save(user).toString());
				
				user = new User("Fadia", "Chalawit", "fadiachalawit@gmail.com", passwordEncoder.encode("fadiachalawit"),
						new ArrayList<Role>());
				user.getRoles().add(repositoryRo.findByName("ROLE_USER"));
				log.info("Preloading " + repositoryU.save(user).toString());
				
				user = new User("Helene", "Kreidieh", "helene@gmail.com", passwordEncoder.encode("helenekreidieh"),
						new ArrayList<Role>());
				user.getRoles().add(repositoryRo.findByName("ROLE_USER"));
				log.info("Preloading " + repositoryU.save(user).toString());
				
				user = new User("Kloe", "Nseir", "kloe@gmail.com", passwordEncoder.encode("kloenseir"),
						new ArrayList<Role>());
				user.getRoles().add(repositoryRo.findByName("ROLE_USER"));
				log.info("Preloading " + repositoryU.save(user).toString());
				
				user = new User("Mania", "Bitar", "maniabitar@gmail.com", passwordEncoder.encode("maniabitar"),
						new ArrayList<Role>());
				user.getRoles().add(repositoryRo.findByName("ROLE_USER"));
				log.info("Preloading " + repositoryU.save(user).toString());
				
				user = new User("Yorgo", "Wakim", "bbbloodyy@gmail.com", passwordEncoder.encode("yorgo"),
						new ArrayList<Role>());
				user.getRoles().add(repositoryRo.findByName("ROLE_USER"));
				log.info("Preloading " + repositoryU.save(user).toString());
				
				user = new User("Ram", "Barada", "rambarada@gmail.com", passwordEncoder.encode("rambarada"),
						new ArrayList<Role>());
				user.getRoles().add(repositoryRo.findByName("ROLE_USER"));
				log.info("Preloading " + repositoryU.save(user).toString());
				
				user = new User("Joelle", "Chaaya", "joelle@gmail.com", passwordEncoder.encode("joelle"),
						new ArrayList<Role>());
				user.getRoles().add(repositoryRo.findByName("ROLE_USER"));
				log.info("Preloading " + repositoryU.save(user).toString());
				
				user = new User("Perla", "Nemer", "perla@gmail.com", passwordEncoder.encode("perla"),
						new ArrayList<Role>());
				user.getRoles().add(repositoryRo.findByName("ROLE_USER"));
				log.info("Preloading " + repositoryU.save(user).toString());
				
				user = new User("Elvana", "Hashem", "elvana@gmail.com", passwordEncoder.encode("elvana"),
						new ArrayList<Role>());
				user.getRoles().add(repositoryRo.findByName("ROLE_USER"));
				log.info("Preloading " + repositoryU.save(user).toString());
				
				user = new User("Rawad", "Geha", "rawadgeha@gmail.com", passwordEncoder.encode("rawad"),
						new ArrayList<Role>());
				user.getRoles().add(repositoryRo.findByName("ROLE_USER"));
				log.info("Preloading " + repositoryU.save(user).toString());
				
				user = new User("Elias", "Azar", "azarr@gmail.com", passwordEncoder.encode("eliasazar"),
						new ArrayList<Role>());
				user.getRoles().add(repositoryRo.findByName("ROLE_USER"));
				log.info("Preloading " + repositoryU.save(user).toString());
				
			} else {
				User user = repositoryU.findByEmail("cinema@gmail.com");
				if(user == null) {
					user = new User("Admin", "Cinema", "cinema@gmail.com", passwordEncoder.encode("admin"),
							new ArrayList<Role>());
					user.getRoles().add(repositoryRo.findByName("ROLE_ADMIN"));
					log.info("Preloading " + repositoryU.save(user).toString());
				}
				else log.info("Admin user already added");
			}
			
			List<Room> rooms = repositoryR.findAll();
			if(rooms.size()==0) {
				log.info("Preloading " + repositoryR.save(new Room("Room I")).toString());
				log.info("Preloading " + repositoryR.save(new Room("Room II")).toString());
				log.info("Preloading " + repositoryR.save(new Room("Room III")).toString());
				log.info("Preloading " + repositoryR.save(new Room("Room IV")).toString());
				log.info("Preloading " + repositoryR.save(new Room("Room V")).toString());
			}
			else log.info("Rooms already added.");
			
			rooms = repositoryR.findAll();
			if(rooms.size()!=0 && repositoryS.findAll().size()==0) {
				for(Room r : rooms) {
					for(int row = 1; row<=10; row++) {
						for(int number = 1; number<= 8; number++) {
							log.info("Preloading " + repositoryS.save(new Seat(row, number, r)).toString());
						}
					}
				}
			}
			else log.info("Seats already added");
			
			List<Category> categories = repositoryC.findAll();
			if(categories.size() == 0) {
				log.info("Preloading " + repositoryC.save(new Category("Action")).toString());
				log.info("Preloading " + repositoryC.save(new Category("Horror")).toString());
				log.info("Preloading " + repositoryC.save(new Category("Romance")).toString());
				log.info("Preloading " + repositoryC.save(new Category("Comedy")).toString());
				log.info("Preloading " + repositoryC.save(new Category("Drama")).toString());
				log.info("Preloading " + repositoryC.save(new Category("Science Fiction")).toString());
				log.info("Preloading " + repositoryC.save(new Category("Mystery")).toString());
			}
			else log.info("Categories already added");
			
			List<Movie> movies = repositoryM.findAll();
			if(movies.size() == 0) {
				log.info("Preloading " + repositoryM.save(new Movie("The Old Guard", "Gina Prince-Bythewood",
						"Charlize Theron, Harry Melling, Kiki Layne, Matthias Schoenaerts, MORE", 
						"A group of mercenaries, all centuries-old immortals with the ablity to heal "
						+ "themselves, discover someone is onto their secret, and they must fight to "
						+ "protect their freedom.",
						125, Date.valueOf("2020-09-10"), "images/theoldguard.jpg",
						"images/theoldguardL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Action"), repositoryC.findByName("Science Fiction"))), "https://www.youtube.com/embed/aK-X2d0lJ_s?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Terminator: Dark Fate", "Tim Miller",
						"Linda Hamilton, Arnold Schwarzenegger, Mackenzie Davis, Natalia Reyes, MORE", 
						"When an advanced Terminator is sent into the past, a cyborg and a seasoned "
						+ "female warrior team up to stop the death of a young woman who is fated to"
						+ " ensure the survival of the human race.",
						128, Date.valueOf("2019-10-23"), "images/terminatordarkfate.jpg",
						"images/terminatordarkfateL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Action"), repositoryC.findByName("Science Fiction"))), "https://www.youtube.com/embed/oxy8udgWRmo?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Suicide Squad", "David Ayer",
						"Margot Robbie, Viola Davis, Jai Courtney, Joel Kinnaman, MORE", 
						"Amanda Waller assembles a team of imprisoned supervillains to execute"
						+ " dangerous black ops missions. When an ancient witch threatens to destroy"
						+ " mankind the squad is sent to stop her.",
						137, Date.valueOf("2016-08-01"), "images/suicidesquad.jpg",
						"images/suicidesquadL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Action"), repositoryC.findByName("Science Fiction"))), "https://www.youtube.com/embed/CmRih_VtVAs?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Gemini Man", "Ang Lee",
						"Will Smith, Mary Elizabeth Winstead, Clive Owen, Benedict Wong, MORE", 
						"Henry is an ageing assassin who finds himself being chased by someone with the "
						+ "ability to predict his moves. He soon discovers that the person is his clone and "
						+ "sets out to discover his origin.",
						117, Date.valueOf("2019-10-03"), "images/geminiman.jpg",
						"images/geminimanL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Action"), repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/AbyJignbSj0?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("John Wick", "Chad Stahelski",
						"Keanu Reeves, Ian McShane, Lance Reddick, Derek Kolstad, MORE", 
						"Legendary assassin John Wick (Keanu Reeves) retired from his violent career "
						+ "after marrying the love of his life. Her sudden death leaves John in deep mourning."
						+ " When sadistic mobster Iosef Tarasov (Alfie Allen) and his thugs steal John's prized "
						+ "car and kill the puppy that was a last gift from his wife, John unleashes the remorseless"
						+ " killing machine within and seeks vengeance. Meanwhile, Iosef's father (Michael Nyqvist)"
						+ " -- John's former colleague -- puts a huge bounty on John's head.",
						102, Date.valueOf("2014-10-30"), "images/johnwick.jpg",
						"images/johnwickL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Action"), repositoryC.findByName("Mystery"))), "https://www.youtube.com/embed/l1g0fn5Nm_g?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("San Andreas", "Brad Peyton",
						"Dwayne Johnson, Alexandra Daddario, Carla Gugino, Paul Giamatti, MORE", 
						"Following a massive earthquake, Ray, a rescue chopper pilot, "
						+ "and his soon to be ex-wife try to find and save their daughter before "
						+ "another disaster strikes.",
						114, Date.valueOf("2015-05-27"), "images/sanandreas.jpg",
						"images/sanandreasL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Action"), repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/xQr_ojji2Ck?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("X-Men: Dark Phoenix", "Simon Kinberg",
						"Sophie Turner, James McAvoy, Jessica Chastain, Michael Fassbender, MORE", 
						"After a mishap, Jean Grey is struck by a powerful ray of energy which she "
						+ "absorbs into her body, turning her into an uncontrollable liability for the X-Men.",
						114, Date.valueOf("2019-06-05"), "images/darkphoenix.jpeg",
						"images/darkphoenixL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Action"), repositoryC.findByName("Science Fiction"))),"https://www.youtube.com/embed/1-q8C_c-nlM?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Rambo: Last Blood", "Adrian Grunberg",
						"Sylvester Stallone, Yvette Monreal, Paz Vega, Sergio Peris-Mencheta, MORE", 
						"John Rambo's peaceful life is interrupted when Gabriela, "
						+ "his friend's granddaughter, disappears in Mexico. He decides to free"
						+ " her from the clutches of local cartels, starting a cycle of revenge.",
						99, Date.valueOf("2019-09-18"), "images/rambolastblood.jpg",
						"images/rambolastbloodL.jpeg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Action"))), "https://www.youtube.com/embed/km_L0v3C0ms?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Assassin's Creed", "Justin Kurzel",
						"Michael Fassbender, Marion Cotillard, Jeremy Irons, MORE", 
						"With the help of technology, Callum learns that he is a descendant of a "
						+ "dangerous society. He travels back in time to learn about his ancestors and acquire "
						+ "skills to defeat an evil organisation.",
						140, Date.valueOf("2007-11-13"), "images/assassinscreed.jpg",
						"images/assassinscreedL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Action"))), "https://www.youtube.com/embed/2hoAkqXonJU?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Saaho", "Sujeeth",
						"Prabhas, Shraddha Kapoor, Neil Nitin Mukesh, Chunky Pandey, MORE", 
						"An undercover agent and his partner go after a thief who has stolen "
						+ "\2,000 crore rupees. Soon, they realise that the case is linked to "
						+ "the death of a crime lord and an emerging gang war.",
						170, Date.valueOf("2019-08-30"), "images/saaho.jpg",
						"images/saahoL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Action"))), "https://www.youtube.com/embed/lD0-ztCFydA?autoplay=1&mute=1&enablejsapi=1")));
				// horror movies
				log.info("Preloading " + repositoryM.save(new Movie("Split", "M. Night Shyamalan",
						"James McAvoy, Anya Taylor-Joy, M. Night Shyamalan, MORE", 
						"Kevin, who is suffering from dissociative identity disorder and has 23"
								+ " alter egos, kidnaps three teenagers. They must figure out his friendly"
								+ " personas before he unleashes his 24th personality.",
						117, Date.valueOf("2017-01-19"), "images/split.jpg",
						"images/splitL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Horror"), repositoryC.findByName("Mystery"))), "https://www.youtube.com/embed/4eHur2tRBx4?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("It", "M. Night ShyamalanAndrés Muschietti",
						"Finn Wolfhard, Bill Skarsgård, Jaeden Martell, Sophia Lillis, MORE",
						"Seven helpless and bullied children are forced to face their worst "
						+ "nightmares when Pennywise, a shape-shifting clown, reappears. The clown, "
						+ "an ancient evil torments children before feeding on them.",
						135, Date.valueOf("2017-09-05"), "images/it.jpg",
						"images/itL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Horror"), repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/xKJmEC5ieOk?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Bird Box", "Susanne Bier",
						"Sandra Bullock, Trevante Rhodes, Sarah Paulson, MORE", 
						"When a mysterious force decimates the population, only one thing is certain -- "
						+ "if you see it, you die. The survivors must now avoid coming face to face with an "
						+ "entity that takes the form of their worst fears. Searching for hope and a new beginning, "
						+ "a woman and her children embark on a dangerous journey through the woods and down a river "
						+ "to find the one place that may offer sanctuary. To make it, they'll have to cover their "
						+ "eyes from the evil that chases them -- and complete the trip blindfolded.",
						124, Date.valueOf("2018-11-12"), "images/birdbox.jpg",
						"images/birdboxL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Horror"), repositoryC.findByName("Science Fiction"), repositoryC.findByName("Mystery"))), "https://www.youtube.com/embed/INJ2bPFy108?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("The Nun", "Corin Hardy",
						"Taissa Farmiga, Bonnie Aarons, Demián Bichir, MORE",
						"When a young nun at a cloistered abbey in Romania takes her own life,"
						+ " a priest with a haunted past and a novitiate on the threshold of her "
						+ "final vows are sent by the Vatican to investigate. Together, they uncover the "
						+ "order's unholy secret. Risking not only their lives but their faith and their "
						+ "very souls, they confront a malevolent force in the form of a demonic nun.",
						96, Date.valueOf("2018-09-04"), "images/thenun.jpg",
						"images/thenunL.jpeg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Horror"))), "https://www.youtube.com/embed/pzD9zGcUNrw?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("A Quiet Place", "John Krasinski",
						"John Krasinski, Emily Blunt, Millicent Simmonds, Noah Jupe, MORE",
						"A family struggles for survival in a world where most humans have been killed by "
						+ "blind but noise-sensitive creatures. They are forced to communicate in sign "
						+ "language to keep the creatures at bay.",
						91, Date.valueOf("2018-04-06"), "images/aquietplace.jpg",
						"images/aquietplaceL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Horror"), repositoryC.findByName("Science Fiction"))), "https://www.youtube.com/embed/tH-dZY_6-b0?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Death Note", "Adam Wingard",
						"Nat Wolff, LaKeith Stanfield, Margaret Qualley, Willem Dafoe, MORE",
						"A high school student discovers a supernatural notebook that has deadly powers."
						+ " He can kill anyone he wishes simply by inscribing their name within its pages."
						+ " Intoxicated with his new power, he begins to eliminate those he deems unworthy of life.",
						101, Date.valueOf("2017-07-20"), "images/deathnote.jpg",
						"images/deathnoteL.jpg",  new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Horror"), repositoryC.findByName("Mystery"))), "https://www.youtube.com/embed/gvxNaSIB_WI?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Veronica", "Paco Plaza",
						"Sandra Escacena, Iván Chavero, Claudia Placer, Bruna González, MORE",
						"During a solar eclipse, young Verónica and her friends want to summon the"
						+ " spirit of Verónica's father using an Ouija board. However, during the session "
						+ "she loses consciousness and soon it becomes clear that evil demons have arrived.",
						106, Date.valueOf("2017-08-25"), "images/veronica.jpg",
						"images/veronicaL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Horror"), repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/lQW5I5tCy28?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Ouija", "Stiles White",
						"Olivia Cooke, Shelley Hennig, Daren Kagasoff, Ana Coto, MORE",
						"Following the sudden death of her best friend, Debbie, "
						+ "Laine finds an antique Ouija board in Debbie's room and tries to use it to "
						+ "say goodbye. Instead, she makes contact with a spirit that calls itself DZ. "
						+ "As strange events begin to occur, Laine enlists others to help her determine "
						+ "DZ's identity and what it wants. As the friends delve deeper, they find that "
						+ "Debbie's mysterious death was not unique, and that they will suffer the same fate"
						+ " unless they learn how to close the portal they've opened.",
						89, Date.valueOf("2014-10-23"), "images/ouija.jpg",
						"images/ouijaL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Horror"))), "https://www.youtube.com/embed/bHxftMH_QE4?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("The Conjuring 2", "James Wan",
						"Vera Farmiga, Patrick Wilson, Madison Wolfe, Frances O'Connor, MORE",
						"Peggy, a single mother of four children, seeks the help of occult investigators"
						+ " Ed and Lorraine Warren when she and her children witness strange, paranormal"
						+ " events in their house.",
						134, Date.valueOf("2016-05-13"), "images/theconjuring2.jpg",
						"images/theconjuring2L.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Horror"), repositoryC.findByName("Mystery"))), "https://www.youtube.com/embed/GHw32DSTIvY?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("The Platform", "Galder Gaztelu-Urrutia",
						"Alexandra Masangkay, Iván Massagué, Zorion Eguileor, Antonia San Juan, MORE",
						"In the future, prisoners housed in vertical cells watch as inmates in the "
						+ "upper cells are fed while those below starve.",
						94, Date.valueOf("2019-09-06"), "images/theplatform.jpg",
						"images/theplatformL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Horror"))), "https://www.youtube.com/embed/RlfooqeZcdY?autoplay=1&mute=1&enablejsapi=1")));
				// romance movies
				log.info("Preloading " + repositoryM.save(new Movie("A Star is Born", "Bradley Cooper",
						"Bradley Cooper, Lady Gaga, Sam Elliott, Dave Chappelle, MORE", 
						"After falling in love with struggling artist Ally, Jackson, a musician,"
						+ " coaxes her to follow her dreams, while he battles with alcoholism"
						+ " and his personal demons.",
						134, Date.valueOf("2018-11-11"), "images/astarisborn.jpg",
						"images/astarisbornL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Romance"), repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/nSbzyEJ8X9E?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Tall Girl", "Nzingha Stewart",
						"Ava Michelle, Sabrina Carpenter, Griffin Gluck, Paris Berelc, MORE", 
						"When the tallest girl in high school falls for a handsome foreign exchange student,"
						+ " she becomes embroiled in a surprising love triangle and realizes she's far more than"
						+ " her insecurities about her height have led her to believe.",
						102, Date.valueOf("2019-09-13"), "images/tallgirl.jpg",
						"images/tallgirlL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Romance"))), "https://www.youtube.com/embed/NfpXeLVzJIw?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("La La Land", "Damien Chazelle",
						"Ryan Gosling, Emma Stone, John Legend, Jessica Rothe, MORE", 
						"Sebastian (Ryan Gosling) and Mia (Emma Stone) are drawn together by their common desire"
						+ " to do what they love. But as success mounts they are faced with decisions that begin "
						+ "to fray the fragile fabric of their love affair, and the dreams they worked so hard to "
						+ "maintain in each other threaten to rip them apart.",
						128, Date.valueOf("2016-08-31"), "images/lalaland.jpg",
						"images/lalalandL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Romance"))), "https://www.youtube.com/embed/0pdqf4P9MB8?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("To All the Boys I've Loved Before", "Susan Johnson",
						"Lana Condor, Noah Centineo, Janel Parrish, Anna Cathcart, MORE", 
						"Shy high school junior Lara Jean Covey writes letters to boys "
						+ "she feels an intense passion for before locking them away in her closet. "
						+ " One night, Lara Jean falls asleep on the couch while hanging out with her little sister, "
						+ "Kitty, who sneaks into her room and finds her collection of letters. The following "
						+ "Monday at school, Lara Jean is confronted by a former crush, Peter Kavinsky. "
						+ "He received the letter she had written him, causing her to faint. After waking up, "
						+ "she sees Josh approaching with his letter, and in a moment of panic, Lara Jean kisses "
						+ "Peter to throw Josh off before running off. Lara Jean then encounters another recipient "
						+ "of a letter, Lucas, who is gay, as she begins to realize that all the letters "
						+ "have been mailed out.",
						99, Date.valueOf("2018-08-17"), "images/toalltheboysivelovedbefore.jpg",
						"images/toalltheboysivelovedbeforeL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Romance"))), "https://www.youtube.com/embed/L76SvHvaSug?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Twilight", "Catherine Hardwicke",
						"Robert Pattinson, Kristen Stewart, Taylor Lautner, Nikki Reed, MORE", 
						"When Bella Swan relocates to Forks, Washington, to live with her father, she meets "
						+ "a mysterious Edward Cullen to whom she finds herself drawn. Later, she discovers that "
						+ "he is a vampire.",
						126, Date.valueOf("2008-12-04"), "images/twilight.jpg",
						"images/twilightL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Romance"))), "https://www.youtube.com/embed/RHtluksWi44?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Portrait of a Lady on Fire", "Céline Sciamma",
						"Adèle Haenel, Noémie Merlant, Luàna Bajrami, Valeria Golino, MORE", 
						"France, 1770. Marianne, a painter, is commissioned to do the wedding portrait of Héloïse,"
						+ " a young woman who has just left the convent. Héloïse is a reluctant bride to be and "
						+ "Marianne must paint her without her knowing. She observes her by day, to paint her secretly.",
						119, Date.valueOf("2019-05-19"), "images/portraitofaladyonfire.jpg",
						"images/portraitofaladyonfireL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Romance"))), "https://www.youtube.com/embed/R-fQPTwma9o?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Friends with Benefits", "Will Gluck",
						"Mila Kunis, Justin Timberlake, Jenna Elfman, Patricia Clarkson, MORE", 
						"Jamie, a New York-based headhunter, gets Dylan to take a job at GQ. They soon become "
						+ "friends but things get complicated after the individually jaded lovers add"
						+ " sex to their relationship.",
						109, Date.valueOf("2011-07-18"), "images/friendswithbenefits.jpg",
						"images/friendswithbenefitsL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Romance"))), "https://www.youtube.com/embed/ZJRIHjlEqd8?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Les Misérables", "Tom Hooper",
						"Anne Hathaway, Hugh Jackman, Russell Crowe, Eddie Redmayne, MORE", 
						"Jean Valjean, a prisoner, breaks parole in order to start life anew. He soon becomes "
						+ "the caretaker of a young girl but his past comes back to catch up with him.",
						160, Date.valueOf("2013-01-03"), "images/lesmiserables.jpg",
						"images/lesmiserablesL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Romance"))), "https://www.youtube.com/embed/iHm7IG2SYHg?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Love & Other Drugs", "Edward Zwick",
						"Anne Hathaway, Jake Gyllenhaal, Gabriel Macht, Hank Azaria, MORE", 
						"Pharmaceutical sales representative and serial womaniser Jake Randall meets "
						+ "his match in Maggie, a young woman who suffers from early-onset Parkinson's "
						+ "disease, just as his career begins to skyrocket.",
						112, Date.valueOf("2010-11-04"), "images/loveandotherdrugs.jpg",
						"images/loveandotherdrugsL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Romance"))), "https://www.youtube.com/embed/h6w7Dh-QxzY?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Titanic", "James Cameron",
						"Leonardo DiCaprio, Kate Winslet, Billy Zane, Kathy Bates, MORE", 
						"Seventeen-year-old Rose hails from an aristocratic family and is set to be married. "
						+ "When she boards the Titanic, she meets Jack Dawson, an artist, and falls"
						+ " in love with him.",
						210, Date.valueOf("1998-02-01"), "images/titanic.jpg",
						"images/titanicL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Romance"))), "https://www.youtube.com/embed/cIJ8ma0kKtY?autoplay=1&mute=1&enablejsapi=1")));
				// comedy movies
				log.info("Preloading " + repositoryM.save(new Movie("Easy A", "Will Gluck",
						"Emma Stone, Amanda Bynes, Penn Badgley, Stanley Tucci, MORE", 
						"While Olive lies to her best friend about losing her virginity to one"
						+ " of the college boys, a girl overhears their conversation. Soon, her story "
						+ "spreads across the entire school like wildfire.",
						92, Date.valueOf("2010-10-28"), "images/easya.jpg",
						"images/easyaL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Comedy"))), "https://www.youtube.com/embed/eCRbC6pq0LA?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Like a Boss", "Miguel Arteta",
						"Salma Hayek, Tiffany Haddish, Rose Byrne, Seth Rollins, MORE", 
						"Mel and Mia, two friends with different ideals, run a cosmetics company"
						+ " but they soon face a financial crisis. When a business magnate offers to"
						+ " buy them out, their friendship is tested.",
						83, Date.valueOf("2020-01-07"), "images/likeaboss.jpg",
						"images/likeabossL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Comedy"))), "https://www.youtube.com/embed/geaQCs4oJvQ?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Work It", "Laura Terruso",
						"Sabrina Carpenter, Jordan Fisher, Liza Koshy, Keiynan Lonsdale, MORE", 
						"An awkward 18-year-old achieves near-perfection by sheer hard work. She "
						+ "vows to transform her gawkiness through dance, and refine her skills until "
						+ "she competes at a competition.",
						93, Date.valueOf("2020-08-07"), "images/workit.jpg",
						"images/workitL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Comedy"))), "https://www.youtube.com/embed/wEhKEec8In4?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Jumanji: The Next Level", "Jake Kasdan",
						"Dwayne Johnson, Kevin Hart, Jack Black, Karen Gillan, MORE", 
						"When Spencer goes missing, the gang returns to Jumanji to travel unexplored"
						+ " territories and help their friend escape the world's most dangerous game.",
						123, Date.valueOf("2019-12-04"), "images/jumanjithenextlevel.jpg",
						"images/jumanjithenextlevelL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Comedy"))), "https://www.youtube.com/embed/FVd23E5sTuc?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("The Kissing Booth", "Vince Marcello",
						"Joey King, Jacob Elordi, Meganne Young, Carson White, MORE", 
						"A high school student finds herself face-to-face with her long-term crush when she"
						+ " signs up to run a kissing booth at the spring carnival.",
						110, Date.valueOf("2018-05-11"), "images/thekissingbooth.jpg",
						"images/thekissingboothL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Comedy"))), "https://www.youtube.com/embed/hifvjuwsqK8?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("The SpongeBob Movie: Sponge on the Run", 
						"Tim Hill",
						"Keanu Reeves, Stephen Hillenburg, Snoop Dogg, Danny Trejo, MORE", 
						"When SpongeBob SquarePants' beloved pet snail Gary goes missing,"
						+ " a path of clues leads SpongeBob and his best friend Patrick to the "
						+ "powerful King Poseidon, who has Gary held captive in the Lost City of Atlantic City. "
						+ "On their mission to save Gary, SpongeBob and the Bikini Bottom gang team up for a heroic and "
						+ "hilarious journey, where they discover nothing is stronger than the power of friendship.",
						100, Date.valueOf("2020-08-14"), "images/spongebob.jpg",
						"images/spongebobL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Comedy"))), "https://www.youtube.com/embed/neBkma9xtjM?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Bridesmaids", "Paul Feig",
						"Melissa McCarthy, Kristen Wiig, Maya Rudolph, Rose Byrne, MORE", 
						"Annie, a jobless chef, is asked by her best friend, Lillian, to be her maid of honour."
						+ " Issues evolve when she ruins the wedding rituals in a rage of conflict with one of"
						+ " the bridesmaids.",
						132, Date.valueOf("2011-04-28"), "images/bridesmaids.jpg",
						"images/bridesmaidsL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Comedy"))), "https://www.youtube.com/embed/RyI5ihZNJXU?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("The Addams Family", " Conrad Vernon, Greg Tiernan",
						"Chloë Grace Moretz, Finn Wolfhard, Oscar Isaac, Charlize Theron, MORE", 
						"The eccentric and macabre Addams family moves to a bland suburb in New Jersey."
						+ " However, problems arise when their child, Wednesday, befriends the daughter of a "
						+ "reality show host.",
						86, Date.valueOf("2019-10-06"), "images/theadamsfamily.jpg",
						"images/theadamsfamilyL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Comedy"))), "https://www.youtube.com/embed/Fq_oxnkt8CM?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Baywatch", "Seth Gordon",
						"Zac Efron, Alexandra Daddario, Dwayne Johnson, Kelly Rohrbach, MORE", 
						"Lifeguard Mitch Buchannon and his team discover a drug racket involving businesswoman "
						+ "Victoria Leeds and decide to unearth the truth and bring the perpetrators to justice.",
						121, Date.valueOf("2017-05-12"), "images/baywatch.jpg",
						"images/baywatchL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Comedy"))), "https://www.youtube.com/embed/FaC-LNlLHIY?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Ocean's 8", "Gary Ross",
						"Sandra Bullock, Cate Blanchett, Anne Hathaway, Rihanna, MORE", 
						"Debbie Ocean is released from jail after serving a prison sentence. "
						+ "She assembles a special crew of seven women to steal a diamond necklace, "
						+ "worth 150 million dollars, from the Met Gala.",
						111, Date.valueOf("2018-06-05"), "images/oceans8.jpg",
						"images/oceans8L.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Comedy"))), "https://www.youtube.com/embed/n5LoVcVsiSQ?autoplay=1&mute=1&enablejsapi=1")));
				// drama movies
				log.info("Preloading " + repositoryM.save(new Movie("The Greatest Showman", "Michael Gracey",
						"Hugh Jackman, Zac Efron, Zendaya, Keala Settle, MORE", 
						"P T Barnum becomes a worldwide sensation in the show business. His imagination"
						+ " and innovative ideas take him to the top of his game.",
						106, Date.valueOf("2017-12-08"), "images/thegreatestshowman.jpg",
						"images/thegreatestshowmanL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/FHPwzZzLLnM?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Me Before You", "Thea Sharrock",
						"Emilia Clarke, Sam Claflin, Matthew Lewis, Charles Dance, MORE", 
						"After becoming unemployed, Louisa Clark is forced to accept one which requires"
						+ " her to take care of Will Traynor, a paralysed man. The two of them soon bond "
						+ "with each other.",
						111, Date.valueOf("2016-05-23"), "images/mebeforeyou.jpg",
						"images/mebeforeyouL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/J-lcPRmI6jY?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("After We Collided", "Roger Kumble",
						"Hero Fiennes Tiffin, Josephine Langford, Dylan Sprouse, Candice King, MORE", 
						"Tessa finds herself struggling with her complicated relationship"
						+ " with Hardin; she faces a dilemma that could change their lives forever.",
						107, Date.valueOf("2020-09-02"), "images/afterwecollided.jpg",
						"images/afterwecollidedL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/2SvwX3ux_-8?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("The Fault in Our Stars", "Josh Boone",
						"Shailene Woodley, Ansel Elgort, Nat Wolff, Willem Dafoe, MORE", 
						"Two cancer-afflicted teenagers Hazel and Augustus meet at a cancer support"
						+ " group. The two of them embark on a journey to visit a reclusive author in Amsterdam.",
						133, Date.valueOf("2014-06-05"), "images/thefaultinourstars.jpg",
						"images/thefaultinourstarsL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/qcKSPo1XNMs?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("All the Bright Places", "Brett Haley",
						"Elle Fanning, Justice Smith, Alexandra Shipp, Keegan-Michael Key, MORE", 
						"After meeting each other, two people struggle with the emotional and physical"
						+ " scars of their past. They discover that even the smallest moments can mean something.",
						108, Date.valueOf("2020-02-28"), "images/allthebrightplaces.jpg",
						"images/allthebrightplacesL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/vtlw2Q6vh6o?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("The Revenant", "Alejandro González Iñárritu",
						"Leonardo DiCaprio, Tom Hardy, Will Poulter, Domhnall Gleeson, MORE", 
						"Hugh Glass, a legendary frontiersman, is severely injured in a bear attack and is"
						+ " abandoned by his hunting crew. He uses his skills to survive and take revenge on"
						+ " his companion who betrayed him.",
						156, Date.valueOf("2015-12-16"), "images/therevenant.jpg",
						"images/therevenantL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/fFD8-YMMJwk?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("The Trial of the Chicago 7", "Aaron Sorkin",
						"Sacha Baron Cohen, Eddie Redmayne, Jeremy Strong, Mark Rylance, MORE", 
						"The film is based on the infamous 1969 trial of seven defendants charged by"
						+ " the federal government with conspiracy and more, arising from the "
						+ "countercultural protests in Chicago at the 1968 Democratic National Convention."
						+ " The trial transfixed the nation and sparked a conversation about mayhem"
						+ " intended to undermine the U.S. government.",
						130, Date.valueOf("2020-09-25"), "images/thetrialofthechicago7.jpg",
						"images/thetrialofthechicago7L.png", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/FVb6EdKDBfU?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Miracle in Cell No. 7", "Mehmet Ada Öztekin",
						"Aras Bulut İynemli, Nisa Sofiya Aksongur, Deniz Baysal, Hayal Köseoğlu, MORE", 
						"A story of love between a mentally-ill father who was wrongly accused"
						+ " of murder and his six year old daughter.",
						132, Date.valueOf("2019-10-10"), "images/miracleincellno7.jpg",
						"images/miracleincellno7L.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/G2wPoBy2JQI?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("The Irishman", "Martin Scorsese",
						"Robert De Niro, Al Pacino, Joe Pesci, Harvey Keitel, MORE", 
						"In the 1950s, truck driver Frank Sheeran gets involved with Russell Bufalino "
						+ "and his Pennsylvania crime family. As Sheeran climbs the ranks to become a"
						+ " top hit man, he also goes to work for Jimmy Hoffa -- a powerful Teamster tied "
						+ "to organized crime.",
						210, Date.valueOf("2019-09-27"), "images/theirishman.jpg",
						"images/theirishmanL.png", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/RS3aHkkfuEI?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Wonder", "Stephen Chbosky",
						"Jacob Tremblay, Julia Roberts, Owen Wilson, Noah Jupe, MORE", 
						"August, a boy with a rare facial abnormality, enters the fifth grade in a private "
						+ "school where he befriends Jack. The two form a strong bond while facing"
						+ " the bullies in their class.",
						113, Date.valueOf("2017-11-14"), "images/wonder.jpg",
						"images/wonderL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/ASAGdtAJPnU?autoplay=1&mute=1&enablejsapi=1")));
				// science fiction movies
				log.info("Preloading " + repositoryM.save(new Movie("Outside the Wire", "Mikael Håfström",
						"Anthony Mackie, Damson Idris, Emily Beecham, Kristina Tonteri-Young, MORE", 
						"In the near future, a drone pilot sent into a war zone finds himself paired"
						+ " up with a top-secret android officer on a mission to stop a nuclear attack.",
						115, Date.valueOf("2021-01-15"), "images/outsidethewire.jpg",
						"images/outsidethewireL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Science Fiction"))), "https://www.youtube.com/embed/u8ZsUivELbs?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Lucy", "Luc Besson",
						"Scarlett Johansson, Luc Besson, Morgan Freeman, Amr Waked, MORE", 
						"Lucy gains extraordinary physical and mental capabilities after "
						+ "the effects of a performance-enhancing drug set in. Soon, she evolves into"
						+ " a warrior bent on destroying those who held her captive.",
						90, Date.valueOf("2014-08-14"), "images/lucy.jpg",
						"images/lucyL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Science Fiction"))), "https://www.youtube.com/embed/l7zAV_MDC68?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Spider-Man: Homecoming", "Jon Watts",
						"Tom Holland, Zendaya, Michael Keaton, Marisa Tomei, MORE", 
						"Peter Parker tries to stop the Vulture from selling weapons made with advanced "
						+ "Chitauri technology while trying to balance his life as an ordinary high school student.",
						133, Date.valueOf("2017-06-28"), "images/spidermanhomecoming.jpg",
						"images/spidermanhomecomingL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Science Fiction"))), "https://www.youtube.com/embed/QY_m-uV3xjo?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Fantastic Four", "Josh Trank",
						"Kate Mara, Michael B. Jordan, Miles Teller, Jamie Bell, MORE", 
						"Four youngsters gain unique superpowers after being teleported to an alternate universe."
						+ " They must unite and use their powers to fight against a computer mechanic, who transforms "
						+ "into an evil doctor.",
						107, Date.valueOf("2015-08-06"), "images/fantastic4.jpg",
						"images/fantastic4L.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Science Fiction"))), "https://www.youtube.com/embed/fpjw6QZ4H7I?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Aquaman", "James Wan",
						"Jason Momoa, Amber Heard, Patrick Wilson, Nicole Kidman, MORE", 
						"Half-human, half-Atlantean Arthur is born with the ability to communicate"
						+ " with marine creatures. He goes on a quest to retrieve the legendary Trident of Atlan"
						+ " and protect the water world.",
						142, Date.valueOf("2018-12-13"), "images/aquaman.jpg",
						"images/aquamanL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Science Fiction"))), "https://www.youtube.com/embed/EwXria8mVxM?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("The Midnight Sky", "George Clooney",
						"George Clooney, Felicity Jones, Sophie Rundle, Kyle Chandler, MORE", 
						"A lone scientist in the Arctic races to contact a crew of "
						+ "astronauts returning home to a mysterious global catastrophe.",
						122, Date.valueOf("2020-12-09"), "images/themidnightsky.jpg",
						"images/themidnightskyL.jpeg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Science Fiction"))), "https://www.youtube.com/embed/Gb8ZbP6qAzE?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Extinction", "Ben Young",
						"Michael Peña, Lizzy Caplan, Israel Broussard, Mike Colter, MORE", 
						"A man's home life starts to suffer when he has recurring nightmares about a "
						+ "destructive and unknown force. He must soon find the strength to save his wife"
						+ " and two daughters when extraterrestrials launch a devastating attack on the planet.",
						95, Date.valueOf("2018-07-27"), "images/extinction.jpg",
						"images/extinctionL.jpeg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Science Fiction"))), "https://www.youtube.com/embed/4-VjHja3RZA?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Oblivion", "Joseph Kosinski",
						"Olga Kurylenko, Tom Cruise, Andrea Riseborough, Morgan Freeman, MORE", 
						"Jack Harper, a drone repairman stationed on Earth that has been ravaged by war with "
						+ "extraterrestrials, questions his identity after rescuing the woman who keeps"
						+ " appearing in his dreams.",
						124, Date.valueOf("2013-04-11"), "images/oblivion.jpg",
						"images/oblivionL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Science Fiction"))), "https://www.youtube.com/embed/j7ldL1366UQ?autoplay=1&mute=1&enablejsapi=1")));
				// mystery movies
				log.info("Preloading " + repositoryM.save(new Movie("Murder Mystery", "Kyle Newacheck",
						"Adam Sandler, Jennifer Aniston, Luke Evans, Gemma Arterton, MORE", 
						"A New York cop and his wife go on a European vacation to reinvigorate the spark in "
						+ "their marriage. A chance encounter leads to them being framed for the murder of an"
						+ " elderly billionaire.",
						97, Date.valueOf("2019-06-14"), "images/murdermystery.jpg",
						"images/murdermysteryL.jpeg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Mystery"))), "https://www.youtube.com/embed/VuO9QoIUnKU?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Now You See Me", "Louis Leterrier",
						"Jesse Eisenberg, Dave Franco, Mark Ruffalo, Woody Harrelson, MORE", 
						"Four street magicians, Daniel, Merritt, Henley and Jack, ransack a huge amount of"
						+ " money belonging to insurance baron Arthur Tressler while being chased by FBI "
						+ "agent Dylan and Interpol agent Alma.",
						125, Date.valueOf("2013-05-21"), "images/nowyouseeme.jpg",
						"images/nowyouseemeL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Mystery"))), "https://www.youtube.com/embed/kMrUHV0VJe4?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("The Poison Rose", 
						"Francesco Cinquemani, Luca Giliberto, George Gallo",
						"Ella Bleu Travolta, Brendan Fraser, Famke Janssen, Morgan Freeman, MORE", 
						"A hard-drinking detective takes on what looks to be a routine missing person case,"
						+ " only to be drawn into a complex interwoven web of crimes, suspects and dead bodies.",
						97, Date.valueOf("2019-05-23"), "images/thepoisonrose.jpg",
						"images/thepoisonroseL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Mystery"))), "https://www.youtube.com/embed/P6So71lcVdI?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Twin Murders: The Silence of the White City", 
						"Daniel Calparsoro",
						"Belén Rueda, Javier Rey, Itziar Ituño, Aura Garrido, MORE", 
						"A detective returns to Vitoria-Gasteiz, Spain, to investigate murders which mimic those "
						+ "of a serial killer who's about to be released from prison.",
						110, Date.valueOf("2019-11-25"), "images/twinmurders.jpg",
						"images/twinmurdersL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Mystery"))), "https://www.youtube.com/embed/g9p7-YPoXEA?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Secret Obsession", "Peter Sullivan",
						"Brenda Song, Mike Vogel, Dennis Haysbert, Daniel Booko, MORE", 
						"When Jennifer wakes up with amnesia after a traumatic attack, her doting husband"
						+ " cares for her. But she soon realises that the danger is far from over.",
						97, Date.valueOf("2019-07-18"), "images/secretobsession.jpg",
						"images/secretobsessionL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Mystery"))), "https://www.youtube.com/embed/Nc6loZU3kjQ?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Into the Labyrinth", "Donato Carrisi",
						"Valentina Bellè, Katsiaryna Shulha, Toni Servillo, Dustin Hoffman, MORE", 
						"When a kidnapping victim turns up alive after 15 years, a profiler and a private "
						+ "investigator work to piece together the mystery.",
						130, Date.valueOf("2019-10-30"), "images/intothelabyrinth.jpg",
						"images/intothelabyrinthL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Mystery"))), "https://www.youtube.com/embed/eFSx22lLxlA?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Nerve", "Ariel Schulman, Henry Joost",
						"Emma Roberts, Dave Franco, Emily Meade, Machine Gun Kelly, MORE", 
						"Industrious high school senior Vee Delmonico (Emma Roberts) is tired of living life "
						+ "on the sidelines. Pressured by her friends, Vee decides to join Nerve, a popular "
						+ "online game that challenges players to accept a series of dares. It's not long "
						+ "before the adrenaline-fueled competition requires her to perform increasingly "
						+ "dangerous stunts. When Nerve begins to take a sinister turn, Vee finds herself in"
						+ " a high-stakes finale that will ultimately determine her entire future.",
						97, Date.valueOf("2016-07-27"), "images/nerve.jpg",
						"images/nerveL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Mystery"))), "https://www.youtube.com/embed/2PR9MOPTI7g?autoplay=1&mute=1&enablejsapi=1")));
			
				// coming soon movies
				
				log.info("Preloading " + repositoryM.save(new Movie("The Forever Purge", "Everardo Valerio Gout",
						"Ana de la Reguera, Josh Lucas, Leven Rambin, Will Patton, MORE", 
						"On the run from a drug cartel, a Mexican couple battle vicious thugs who plan to continue the violent tradition of the now-outlawed Purge.",
						150, Date.valueOf("2021-07-01"), "images/theForeverPurge.jpeg",
						"images/theForeverPurgeL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Horror"), repositoryC.findByName("Action"), repositoryC.findByName("Science Fiction"))), "https://www.youtube.com/embed/xOrXpK-rUaI?autoplay=1&mute=1&enablejsapi=1" )));
				log.info("Preloading " + repositoryM.save(new Movie("Death on the Nile", "Kenneth Branagh",
						"Kenneth Branagh, Gal Gadot, Armie Hammer, Letitia Wright, MORE", 
						"Detective Hercule Poirot investigates the murder of a young heiress aboard a cruise ship on the Nile River.",
						134, Date.valueOf("2022-02-11"), "images/deathOnTheNile.jpg",
						"images/deathOnTheNileL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Mystery"), repositoryC.findByName("Drama"))),"https://www.youtube.com/embed/JM1U-Whb-P0?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Minions: The Rise of Gru", "Kyle Balda",
						"Taraji P. Henson, Steve Carell, Jean-Claude Van Damme, Julie Andrews, MORE", 
						"The untold story of one 12-year-old's dream to become the world's greatest supervillain.",
						90, Date.valueOf("2022-07-01"), "images/minionsTheRiseOfGruL.jpg",
						"images/minionsTheRiseOfGru.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Comedy"))), "https://www.youtube.com/embed/3Y9XeruN5RY?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Hotel Transylvania 4", "Derek Drymon, Jennifer Kluska",
						"Adam Sandler, Selena Gomez, Andy Samberg, Asher Blinkoff, MORE", 
						"Hotel Transylvania: Transformania is an upcoming American computer-animated monster comedy film produced by Sony Pictures Animation.",
						150, Date.valueOf("2021-07-23"), "images/hotelTransylvania.jpg",
						"images/hotelTransylvaniaL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Comedy"))), "https://www.youtube.com/embed/ldBroBrV6Fg?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("West Side Story", "Steven Spielberg",
						"Rachel Zegler, Maddie Ziegler, Ansel Elgort, Ariana DeBose, MORE", 
						"Two teenagers from different ethnic backgrounds fall in love in 1950s New York City.",
						150, Date.valueOf("2021-12-09"), "images/westSideStory.jpg",
						"images/westSideStoryL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Romance"))), "https://www.youtube.com/embed/WJCo_e2g5cI?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Hitman's Wife's Bodyguard", "Patrick Hughes",
						"Salma Hayek, Samuel L. Jackson, Gabriella Wright, Frank Grillo, MORE", 
						"The world's top protection agent is called upon to guard the life of his mortal enemy, "
						+ "one of the world's most notorious hit men. The relentless bodyguard and manipulative assassin have been on the opposite end "
						+ "of the bullet for years and are thrown together for a wildly outrageous 24 hours. During their journey from England to the Hague,"
						+ " they encounter high-speed car chases, outlandish boat escapades and a merciless Eastern European dictator who is out for blood.",
						120, Date.valueOf("2021-07-16"), "images/hitmanBodyguard.jpg",
						"images/hitmanBodyguardL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Action"), repositoryC.findByName("Comedy"))), "https://www.youtube.com/embed/VjHMmF7HsoU?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Don't Breathe 2", "Rodo Sayagues",
						"Bobby Schofield, Christian Zagia, Rocci Williams, Adam Young, MORE", 
						"A blind veteran must use his military training to save a young orphan from a group of kidnappers.",
						150, Date.valueOf("2021-08-12"), "images/dontBreathe.jpg",
						"images/dontBreatheL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Horror"))), "https://www.youtube.com/embed/Fp7PZbBO8F4?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("In the Heights", "Jon M. Chu",
						"Anthony Ramos, Lin‑Manuel Miranda, Stephanie Beatriz, Melissa Barrera, MORE", 
						"A bodega owner has mixed feelings about closing his store and retiring to the Dominican Republic after inheriting his grandmother's fortune.",
						143, Date.valueOf("2021-06-09"), "images/inTheHeights.jpeg",
						"images/inTheHeightsL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Drama"))), "https://www.youtube.com/embed/U0CL-ZSuCrQ?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Space Jam: A New Legacy", "Malcolm D. Lee",
						"LeBron James, Cassandra Starr, Kyrie Irving, Jim Carrey, MORE", 
						"Basketball superstar LeBron James teams up with the Looney Tunes gang to defeat the Goon Squad and save his son.",
						150, Date.valueOf("2021-07-14"), "images/spaceJam.png",
						"images/spaceJamL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Comedy"))), "https://www.youtube.com/embed/0H2cIbUGJJc?autoplay=1&mute=1&enablejsapi=1")));
				log.info("Preloading " + repositoryM.save(new Movie("Dune", "Denis Villeneuve",
						"Zendaya, Timothée Chalamet, Rebecca Ferguson, Jason Momoa, MORE", 
						"Paul Atreides leads nomadic tribes in a battle to control the desert planet Arrakis..",
						150, Date.valueOf("2021-10-01"), "images/dune.jpg",
						"images/duneL.jpg", new ArrayList<Category>(Arrays.asList(repositoryC.findByName("Science Fiction"))), "https://www.youtube.com/embed/n9xhJrPXop4?autoplay=1&mute=1&enablejsapi=1")));
			
			}
			else log.info("Movies already added");
			List<Screening> screenings = repositorySc.findAll();
			if(screenings.size()==0) {
				LocalDateTime DT = LocalDateTime.now().minusDays(10);
				DT = this.checkTime(DT);
				for(int i=0; i<10; i++) {
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Outside the Wire"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Midnight Sky"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Trial of the Chicago 7"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Old Guard"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("After We Collided"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(140);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The SpongeBob Movie: Sponge on the Run"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Work It"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("All the Bright Places"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Old Guard"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Outside the Wire"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(140);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Like a Boss"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Jumanji: The Next Level"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Twin Murders: The Silence of the White City"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Into the Labyrinth"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Terminator: Dark Fate"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(140);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Miracle in Cell No. 7"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Addams Family"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Gemini Man"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Into the Labyrinth"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("After We Collided"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(140);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Outside the Wire"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Midnight Sky"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Trial of the Chicago 7"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Old Guard"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("After We Collided"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(140);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Irishman"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Rambo: Last Blood"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Tall Girl"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Platform"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Saaho"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(210);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Secret Obsession"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Murder Mystery"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("X-Men: Dark Phoenix"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Platform"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Poison Rose"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(130);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The SpongeBob Movie: Sponge on the Run"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Work It"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("All the Bright Places"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Old Guard"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Outside the Wire"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(140);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Portrait of a Lady on Fire"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Aquaman"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Bird Box"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("A Star is Born"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Nun"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(150);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Outside the Wire"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Midnight Sky"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Trial of the Chicago 7"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Old Guard"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("After We Collided"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(140);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("To All the Boys I've Loved Before"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Extinction"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Ocean's 8"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Kissing Booth"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("A Quiet Place"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(115);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Jumanji: The Next Level"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Addams Family"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Like a Boss"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Twin Murders: The Silence of the White City"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("All the Bright Places"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(125);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Greatest Showman"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Wonder"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("It"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Veronica"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Death Note"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(135);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Spider-Man: Homecoming"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Baywatch"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Split"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("La La Land"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Suicide Squad"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(140);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("To All the Boys I've Loved Before"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Extinction"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Ocean's 8"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Kissing Booth"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("A Quiet Place"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(115);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Nerve"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Me Before You"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Conjuring 2"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Revenant"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("San Andreas"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(160);
					DT = this.checkTime(DT);
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Ouija"),
							repositoryR.findByName("Room I"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Lucy"),
							repositoryR.findByName("Room II"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Fault in Our Stars"),
							repositoryR.findByName("Room III"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("Now You See Me"),
							repositoryR.findByName("Room IV"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					log.info("Preloading " + repositorySc.save(new Screening(
							repositoryM.findByTitle("The Nun"),
							repositoryR.findByName("Room V"),
							Date.valueOf(DT.toLocalDate()),
							Time.valueOf(DT.toLocalTime()),
							(float) 15
							)));
					DT = DT.plusMinutes(145);
					DT = this.checkTime(DT);
				}
			}
			else log.info("Screenings already added");
			
			List<Reservation> ress = reservationRep.findAll();
			if(ress.size() == 0) {
				List<Long> screeningsIds = new ArrayList<Long>();
				long scNb = repositorySc.count();
				for(int i=0; i<scNb; i++) {
					screeningsIds.add((long) i);
				}
				Collections.shuffle(screeningsIds);
				LocalDateTime DT = LocalDateTime.now().minusDays(8);
				users = repositoryU.findAll();
				for(int i=0; i<12; i++) {
					for(User u : users) {
						boolean admin = false;
						for(Role r : u.getRoles()) {
							if(r.getName().equals("ROLE_ADMIN")) {
								admin = true;
							}
						}
						if(admin) continue;
						Random rand = new Random();
						int upper = 10;
						int nbReservation = rand.nextInt(upper);
						for(int j=0; j<nbReservation; j++) {
							Random random = new Random();
							Screening screening = null;
							int index = 0;
							while(screening == null) {
								index = random.nextInt((int) screeningsIds.size());
								screening = repositorySc.findById(screeningsIds.get(index)).orElse(null);
							}
							screeningsIds.remove(index);
							int nbSeats = 0;
							while(nbSeats==0){
								nbSeats = random.nextInt(40);
							}
							Reservation r = new Reservation(u, screening, Date.valueOf(DT.toLocalDate()), Time.valueOf(DT.toLocalTime()), nbSeats * screening.getPrice());
							r = reservationRep.save(r);
							log.info("Preloading : " + r);
							for(int k=0; k<nbSeats; k++) {
								long seatId = (screening.getRoom().getId()-1) + k + 20;
								SeatReserved sss = new SeatReserved(repositoryS.getOne(seatId), r, screening);
								log.info("Preloading : "+reservedRep.save(sss));
							}
						}
					}
					DT = DT.plusDays(1);
				}
			}
		};
	}

	private LocalDateTime checkTime(LocalDateTime DT) {
		if(DT.getHour()>23) {
			DT.plusDays(1);
		}
		if(DT.getHour()<12) {
			DT = LocalDateTime.of(DT.getYear(), DT.getMonth(), DT.getDayOfMonth(), 12, 0);
		}
		return DT;
	}
}
