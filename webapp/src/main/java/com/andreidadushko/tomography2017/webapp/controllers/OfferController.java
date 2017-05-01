package com.andreidadushko.tomography2017.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.andreidadushko.tomography2017.webapp.models.OfferModel;

@RestController
@RequestMapping("/offer")
public class OfferController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OfferController.class);
	
	@Inject
	private IOfferService offerService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		Offer offer = offerService.get(id);
		OfferModel convertedOffer = null;
		if (offer != null) {
			convertedOffer = offerEntity2OfferModel(offer);
		}
		return new ResponseEntity<OfferModel>(convertedOffer, HttpStatus.OK);
	}

	@RequestMapping(value = "/count", method = RequestMethod.GET)
	public ResponseEntity<?> getCount() {
		Integer result = offerService.getCount();
		return new ResponseEntity<IntegerModel>(new IntegerModel(result), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody OfferModel offerModel) {
		Offer offer = offerModel2OfferEntity(offerModel);
		try {
			offerService.insert(offer);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (UnsupportedOperationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<IntegerModel>(new IntegerModel(offer.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody OfferModel offerModel) {
		Offer offer = offerModel2OfferEntity(offerModel);
		try {
			offerService.update(offer);
		} catch (IllegalArgumentException e) {
			LOGGER.warn(e.getMessage());
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (UnsupportedOperationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		offerService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAll() {
		List<Offer> list = offerService.getAll();
		List<OfferModel> convertedOfferList = new ArrayList<OfferModel>();
		for (Offer offer : list) {
			convertedOfferList.add(offerEntity2OfferModel(offer));
		}
		return new ResponseEntity<List<OfferModel>>(convertedOfferList, HttpStatus.OK);
	}

	@RequestMapping(value = "/category/{categoryId}", method = RequestMethod.GET)
	public ResponseEntity<?> getByCategoryId(@PathVariable Integer categoryId) {
		List<Offer> list = offerService.getByCategoryId(categoryId);
		List<OfferModel> convertedOfferList = new ArrayList<OfferModel>();
		for (Offer offer : list) {
			convertedOfferList.add(offerEntity2OfferModel(offer));
		}
		return new ResponseEntity<List<OfferModel>>(convertedOfferList, HttpStatus.OK);
	}

	private OfferModel offerEntity2OfferModel(Offer offer) {
		OfferModel offerModel = new OfferModel();
		offerModel.setId(offer.getId());
		offerModel.setName(offer.getName());
		offerModel.setPrice(offer.getPrice());
		offerModel.setCategorId(offer.getCategorId());
		return offerModel;
	}

	public static Offer offerModel2OfferEntity(OfferModel offerModel) {
		Offer offer = new Offer();
		offer.setId(offerModel.getId());
		offer.setName(offerModel.getName());
		offer.setPrice(offerModel.getPrice());
		offer.setCategorId(offerModel.getCategorId());
		return offer;
	}
}
