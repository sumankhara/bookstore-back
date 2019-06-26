package com.pluralsight.bookstore.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@Entity
@ApiModel(description = "Book resource representation")
public class Book {

	@Id
	@GeneratedValue
	@ApiModelProperty("Identifier")
	private Long id;
	
	@Column(length = 250)
	@NotNull
	@Size(min = 1, max = 200)
	@ApiModelProperty("Title of the book")
	private String title;
	
	@Column(length = 10000)
	@Size(min = 1, max = 10000)
	@ApiModelProperty("Description of the book")
	private String description;
	
	@Column(name = "unit_cost")
	@Min(1)
	private Double unitCost;
	
	@Column(length = 50)
	@NotNull
	@Size(min = 1, max = 50)
	private String isbn;
	
	@Column(name = "publication_date")
	@Temporal(TemporalType.DATE)
	@Past
	private Date publicationDate;
	
	@Column(name = "nb_of_pages")
	private Integer nbOfPages;

	@Column(name = "image_url")
	private String imageUrl;
	
	private Language language;
	
	public Book() {
		
	}
	
	public Book(String isbn, String title, Double unitCost, Integer nbOfPages, Language language, Date publicationDate,
			String imageUrl, String description) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.unitCost = unitCost;
		this.nbOfPages = nbOfPages;
		this.language = language;
		this.publicationDate = publicationDate;
		this.imageUrl = imageUrl;
		this.description = description;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Integer getNbOfPages() {
		return nbOfPages;
	}

	public void setNbOfPages(Integer nbOfPages) {
		this.nbOfPages = nbOfPages;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", description=" + description + ", unitCost=" + unitCost
				+ ", isbn=" + isbn + ", publicationDate=" + publicationDate + ", nbOfPages=" + nbOfPages + ", imageUrl="
				+ imageUrl + ", language=" + language + "]";
	}
	
	
}
