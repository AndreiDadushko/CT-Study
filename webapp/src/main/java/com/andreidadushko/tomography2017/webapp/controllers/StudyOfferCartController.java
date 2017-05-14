package com.andreidadushko.tomography2017.webapp.controllers;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.andreidadushko.tomography2017.dao.db.custom.models.StudyOfferCartForList;
import com.andreidadushko.tomography2017.datamodel.Study;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;
import com.andreidadushko.tomography2017.services.IStudyOfferCartService;
import com.andreidadushko.tomography2017.services.IStudyService;
import com.andreidadushko.tomography2017.webapp.models.CartForListModel;
import com.andreidadushko.tomography2017.webapp.models.IntegerModel;
import com.andreidadushko.tomography2017.webapp.models.StudyOfferCartModel;
import com.andreidadushko.tomography2017.webapp.models.StudyWithOffersModel;
import com.andreidadushko.tomography2017.webapp.storage.CurrentUserData;

@RestController
@RequestMapping("/cart")
public class StudyOfferCartController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudyOfferCartController.class);

	@Inject
	private ApplicationContext context;

	@Inject
	private IStudyOfferCartService studyOfferCartService;

	@Inject
	private IStudyService studyService;

	@Inject
	private ConversionService conversionService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		StudyOfferCart studyOfferCart = studyOfferCartService.get(id);
		if (studyOfferCart == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		Study study = studyService.get(studyOfferCart.getStudyId());
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")
				|| userAuthStorage.getId().equals(study.getPersonId())) {
			StudyOfferCartModel convertedCart = null;
			if (studyOfferCart != null) {
				convertedCart = conversionService.convert(studyOfferCart, StudyOfferCartModel.class);
			}
			LOGGER.info("{} request cart with id = {}", userAuthStorage, id);
			return new ResponseEntity<StudyOfferCartModel>(convertedCart, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody StudyOfferCartModel cartModel) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			StudyOfferCart studyOfferCart = conversionService.convert(cartModel, StudyOfferCart.class);
			try {
				studyOfferCartService.insert(studyOfferCart);
				LOGGER.info("{} insert cart with id = {}", userAuthStorage, studyOfferCart.getId());
				return new ResponseEntity<IntegerModel>(new IntegerModel(studyOfferCart.getId()), HttpStatus.CREATED);
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
	public ResponseEntity<?> update(@RequestBody StudyOfferCartModel cartModel) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			StudyOfferCart studyOfferCart = conversionService.convert(cartModel, StudyOfferCart.class);
			try {
				studyOfferCartService.update(studyOfferCart);
				LOGGER.info("{} update cart with id = {}", userAuthStorage, studyOfferCart.getId());
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
		StudyOfferCart studyOfferCart = studyOfferCartService.get(id);
		if (studyOfferCart == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		Study study = studyService.get(studyOfferCart.getStudyId());
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")
				|| userAuthStorage.getId().equals(study.getPersonId())) {
			studyOfferCartService.delete(id);
			LOGGER.info("{} delete cart with id = {}", userAuthStorage, id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(method = RequestMethod.PATCH)
	public ResponseEntity<?> massDelete(@RequestBody Integer[] idArray) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			studyOfferCartService.massDelete(idArray);
			LOGGER.info("{} delete carts with id = {}", userAuthStorage, Arrays.asList(idArray));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/study/{studyId}", method = RequestMethod.GET)
	public ResponseEntity<?> getCartByStudyId(@PathVariable Integer studyId) {
		Study study = studyService.get(studyId);
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Администратор")
				|| userAuthStorage.getPositions().contains("Врач-рентгенолог")
				|| userAuthStorage.getId().equals(study.getPersonId())) {
			List<StudyOfferCartForList> list = studyOfferCartService.getCartByStudyId(studyId);
			List<CartForListModel> convertedCartForList = new ArrayList<CartForListModel>();
			for (StudyOfferCartForList cartForList : list) {
				convertedCartForList.add(conversionService.convert(cartForList, CartForListModel.class));
			}
			LOGGER.info("{} request carts with study id = {}", userAuthStorage, studyId);
			return new ResponseEntity<List<CartForListModel>>(convertedCartForList, HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
	}

	@RequestMapping(value = "/massinsert", method = RequestMethod.POST)
	public ResponseEntity<?> massInsert(@RequestBody StudyWithOffersModel studyOffersModel) {
		CurrentUserData userAuthStorage = context.getBean(CurrentUserData.class);
		if (userAuthStorage.getPositions().contains("Врач-рентгенолог")) {
			Integer studyId = studyOffersModel.getStudyId().getNumber();
			IntegerModel[] offerIdModelArray = studyOffersModel.getOfferIdArray();
			Integer[] offerIdArray = new Integer[offerIdModelArray.length];
			for (int i = 0; i < offerIdModelArray.length; i++) {
				offerIdArray[i] = offerIdModelArray[i].getNumber();
			}
			try {
				studyOfferCartService.massInsert(studyId, offerIdArray);
				LOGGER.info("{} insert carts with study id = {} and offer id = {}", userAuthStorage, studyId,
						Arrays.asList(offerIdArray));
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

}
