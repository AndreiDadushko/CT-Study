package com.andreidadushko.tomography2017.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andreidadushko.tomography2017.datamodel.Offer;
import com.andreidadushko.tomography2017.services.IOfferService;
import com.andreidadushko.tomography2017.webapp.models.IntegerModel;
import com.andreidadushko.tomography2017.webapp.models.OfferFullModel;
import com.andreidadushko.tomography2017.webapp.models.OfferModel;
import com.andreidadushko.tomography2017.webapp.storage.CurrentUserData;

@RestController
@RequestMapping("/offer")
public class OfferController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OfferController.class);

	@Inject
	private ApplicationContext context;

	@Inject
	private IOfferService offerService;

	@Inject
	private ConversionService conversionService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Offer offer = offerService.get(id);
		OfferModel convertedOffer = null;
		if (offer != null) {
			convertedOffer = conversionService.convert(offer, OfferModel.class);
		}
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		LOGGER.info("{} request offer with id = {}", userAuthStorage, id);
		return new ResponseEntity<OfferModel>(convertedOffer, HttpStatus.OK);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResponseEntity<?> getCount() {
		Integer result = offerService.getCount();
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		LOGGER.info("{} request count of offers", userAuthStorage);
		return new ResponseEntity<IntegerModel>(new IntegerModel(result), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody OfferFullModel offerFullModel) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")) {
			Offer offer = conversionService.convert(offerFullModel, Offer.class);
			try {
				offerService.insert(offer);
				LOGGER.info("{} insert offer with id = {}", userAuthStorage, offer.getId());
				return new ResponseEntity<IntegerModel>(new IntegerModel(offer.getId()), HttpStatus.CREATED);
			} catch (IllegalArgumentException e) {
				LOGGER.info("{} has entered incorrect data : {}", userAuthStorage, e.getMessage());
				return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
			} catch (org.springframework.dao.DataIntegrityViolationException e) {
				return new ResponseEntity<IntegerModel>(HttpStatus.CONFLICT);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody OfferFullModel offerFullModel) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")) {
			Offer offer = conversionService.convert(offerFullModel, Offer.class);
			try {
				offerService.update(offer);
				LOGGER.info("{} update category with id = {}", userAuthStorage, offer.getId());
				return new ResponseEntity<>(HttpStatus.CREATED);
			} catch (IllegalArgumentException e) {
				LOGGER.info("{} has entered incorrect data : {}", userAuthStorage, e.getMessage());
				return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
			} catch (org.springframework.dao.DataIntegrityViolationException e) {
				return new ResponseEntity<IntegerModel>(HttpStatus.CONFLICT);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")) {
			try {
				offerService.delete(id);
				LOGGER.info("{} delete offer with id = {}", userAuthStorage, id);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} catch (org.springframework.dao.DataIntegrityViolationException e) {
				return new ResponseEntity<IntegerModel>(HttpStatus.CONFLICT);
			}
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		List<Offer> list = offerService.getAll();
		List<OfferModel> convertedOfferList = new ArrayList<OfferModel>();
		for (Offer offer : list) {
			convertedOfferList.add(conversionService.convert(offer, OfferModel.class));
		}
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		LOGGER.info("{} request all offers", userAuthStorage);
		return new ResponseEntity<List<OfferModel>>(convertedOfferList, HttpStatus.OK);
	}

	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<?> getByCategoryId(@PathVariable Integer categoryId) {
		List<Offer> list = offerService.getByCategoryId(categoryId);
		List<OfferModel> convertedOfferList = new ArrayList<OfferModel>();
		for (Offer offer : list) {
			convertedOfferList.add(conversionService.convert(offer, OfferModel.class));
		}
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		LOGGER.info("{} request all offers with category id = {}", userAuthStorage, categoryId);
		return new ResponseEntity<List<OfferModel>>(convertedOfferList, HttpStatus.OK);
	}

}
