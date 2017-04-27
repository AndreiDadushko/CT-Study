package com.andreidadushko.tomography2017.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.andreidadushko.tomography2017.dao.db.custom.models.StudyOfferCartForList;
import com.andreidadushko.tomography2017.datamodel.StudyOfferCart;
import com.andreidadushko.tomography2017.services.IStudyOfferCartService;
import com.andreidadushko.tomography2017.webapp.models.CartForListModel;
import com.andreidadushko.tomography2017.webapp.models.IntegerModel;
import com.andreidadushko.tomography2017.webapp.models.StudyOfferCartModel;
import com.andreidadushko.tomography2017.webapp.models.StudyWithOffersModel;

@RestController
@RequestMapping("/cart")
public class StudyOfferCartController {

	@Inject
	private IStudyOfferCartService studyOfferCartService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathVariable Integer id) {
		StudyOfferCart studyOfferCart = studyOfferCartService.get(id);
		StudyOfferCartModel convertedCart = null;
		if (studyOfferCart != null) {
			convertedCart = cartEntity2CartModel(studyOfferCart);
		}
		return new ResponseEntity<StudyOfferCartModel>(convertedCart, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> insert(@RequestBody StudyOfferCartModel cartModel) {
		StudyOfferCart studyOfferCart = cartModel2CartEntity(cartModel);
		try {
			studyOfferCartService.insert(studyOfferCart);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (UnsupportedOperationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<IntegerModel>(new IntegerModel(studyOfferCart.getId()), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody StudyOfferCartModel cartModel) {
		StudyOfferCart studyOfferCart = cartModel2CartEntity(cartModel);
		try {
			studyOfferCartService.update(studyOfferCart);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (UnsupportedOperationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		studyOfferCartService.delete(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<?> massDelete(@RequestBody Integer[] idArray) {
		studyOfferCartService.massDelete(idArray);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/study/{studyId}", method = RequestMethod.GET)
	public ResponseEntity<?> getCartByStudyId(@PathVariable Integer studyId) {
		List<StudyOfferCartForList> list = studyOfferCartService.getCartByStudyId(studyId);
		List<CartForListModel> convertedCartForList = new ArrayList<CartForListModel>();
		for (StudyOfferCartForList cartForList : list) {
			convertedCartForList.add(CartForListModel2CartForListEntity(cartForList));
		}
		return new ResponseEntity<List<CartForListModel>>(convertedCartForList, HttpStatus.OK);
	}

	@RequestMapping(value = "/massinsert", method = RequestMethod.POST)
	public ResponseEntity<?> massInsert(@RequestBody StudyWithOffersModel studyOffersModel) {
		Integer studyId = studyOffersModel.getStudyId().getNumber();
		IntegerModel[] offerIdModelArray = studyOffersModel.getOfferIdArray();
		Integer[] offerIdArray = new Integer[offerIdModelArray.length];
		for (int i = 0; i < offerIdModelArray.length; i++) {
			offerIdArray[i] = offerIdModelArray[i].getNumber();
		}
		try {
			studyOfferCartService.massInsert(studyId, offerIdArray);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.BAD_REQUEST);
		} catch (UnsupportedOperationException e) {
			return new ResponseEntity<IntegerModel>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	private StudyOfferCartModel cartEntity2CartModel(StudyOfferCart studyOfferCart) {
		StudyOfferCartModel cartModel = new StudyOfferCartModel();
		cartModel.setId(studyOfferCart.getId());
		cartModel.setPaid(studyOfferCart.getPaid());
		cartModel.setPayDate(studyOfferCart.getPayDate() == null ? null : studyOfferCart.getPayDate().getTime());
		cartModel.setStudyId(studyOfferCart.getStudyId());
		cartModel.setOfferId(studyOfferCart.getOfferId());
		return cartModel;
	}

	private StudyOfferCart cartModel2CartEntity(StudyOfferCartModel cartModel) {
		StudyOfferCart studyOfferCart = new StudyOfferCart();
		studyOfferCart.setId(cartModel.getId());
		studyOfferCart.setPaid(cartModel.getPaid());
		studyOfferCart
				.setPayDate(cartModel.getPayDate() == null ? null : new java.sql.Timestamp(cartModel.getPayDate()));
		studyOfferCart.setStudyId(cartModel.getStudyId());
		studyOfferCart.setOfferId(cartModel.getOfferId());
		return studyOfferCart;
	}

	private CartForListModel CartForListModel2CartForListEntity(StudyOfferCartForList studyOfferCartForList) {
		CartForListModel cartForListModel = new CartForListModel();
		cartForListModel.setId(studyOfferCartForList.getId());
		cartForListModel.setCategory(studyOfferCartForList.getCategory());
		cartForListModel.setOffer(studyOfferCartForList.getOffer());
		cartForListModel.setPrice(studyOfferCartForList.getPrice());
		cartForListModel.setPaid(studyOfferCartForList.getPaid());
		cartForListModel.setPayDate(
				studyOfferCartForList.getPayDate() == null ? null : studyOfferCartForList.getPayDate().getTime());
		return cartForListModel;
	}
}
