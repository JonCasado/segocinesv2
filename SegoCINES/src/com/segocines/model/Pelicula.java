package com.segocines.model;

public class Pelicula
{
	private String img, imgPrevia;
	private String nombre, nombreOrig;
	private String sinopsis, edad, pais, genero;
	private String horarioA, horarioB;
	private String trailer;
	private int anyo, duracion;
	
	public Pelicula(String img, String imgPrevia, String nombre, String nombreOrig, String sinopsis, int anyo, String edad, String pais, String genero, String horarioA, String horarioB, int duracion, String trailer)
	{
		this.img = img;
		this.imgPrevia = imgPrevia;
		this.nombre = nombre;
		this.nombreOrig = nombreOrig;
		this.sinopsis = sinopsis;
		this.edad = edad;
		this.pais = pais;
		this.genero = genero;
		this.horarioA = horarioA;
		this.horarioB = horarioB;
		this.trailer = trailer;
		this.anyo = anyo;
		this.duracion = duracion;
	}
	
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getImgPrevia() {
		return imgPrevia;
	}
	public void setImgPrevia(String imgPrevia) {
		this.imgPrevia = imgPrevia;
	}
	public String getNombreOrig() {
		return nombreOrig;
	}
	public void setNombreOrig(String nombreOrig) {
		this.nombreOrig = nombreOrig;
	}
	public String getSinopsis() {
		return sinopsis;
	}
	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}
	public int getAnyo() {
		return anyo;
	}
	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}
	public String getHorarioA() {
		return horarioA;
	}
	public void setHorarioA(String horarioA) {
		this.horarioA = horarioA;
	}
	public String getHorarioB() {
		return horarioB;
	}
	public void setHorarioB(String horarioB) {
		this.horarioB = horarioB;
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getEdad() {
		return edad;
	}
	public void setEdad(String edad) {
		this.edad = edad;
	}


	public String getTrailer() {
		return trailer;
	}


	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
}
