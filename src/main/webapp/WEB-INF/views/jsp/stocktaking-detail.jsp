<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<jsp:include page="../../../head.jsp"></jsp:include>
<body>
	<div id="stocktaking" data-role="page" data-theme="a">
		<div data-role="header" data-position="fixed">
			<h2>Item List</h2>
			<a id="countItem" href="#" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-right ui-btn-icon-notext ui-icon-plus ui-btn-right btn-success" data-transition="slide">&nbsp;</a>
		</div>
		
		<div role="main" class="ui-content">
			<ul id="items-in-order-list" data-role="listview" data-inset="false">
				<li class="items-in-order-item">
					<h3>NOP1/1 Panhead Screw JP+-M3.5x10R (7.2) CR3Z</h3>
					<div class="ui-grid-a">
						<div class="ui-block-a" style="width:70%">
							<p>WP-SHM 3.5 RoHS_[V-11046]</p>
						</div><!-- .ui-block-a -->
						<div class="ui-block-b" style="width:30%">
							<p class="text-right">62,720 pcs</p>
						</div><!-- .ui-block-b -->
					</div><!-- .ui-grid-a -->
					<div class="action-block">
						<button type="button" class="ui-btn ui-corner-all ui-btn-inline ui-mini btn-success edit-price-btn" data-rel="popup">Edit</button>
						<button type="button" class="ui-btn ui-corner-all ui-btn-inline ui-mini btn-danger delete-btn" data-rel="popup">Delete</button>
					</div>
				</li>
				<li class="items-in-order-item">
					<h3>NOP2 Hexnut น๊อตตัวเมีย-ชุบ CR3Z</h3>
					<div class="ui-grid-a">
						<div class="ui-block-a" style="width:70%">
							<p>WP-SHM 3.5 RoHS_[V-11046]</p>
						</div><!-- .ui-block-a -->
						<div class="ui-block-b" style="width:30%">
							<p class="text-right">61,720 pcs</p>
						</div><!-- .ui-block-b -->
					</div><!-- .ui-grid-a -->
					<div class="action-block">
						<button type="button" class="ui-btn ui-corner-all ui-btn-inline ui-mini btn-success edit-price-btn" data-rel="popup">Edit</button>
						<button type="button" class="ui-btn ui-corner-all ui-btn-inline ui-mini btn-danger delete-btn" data-rel="popup">Delete</button>
					</div>
				</li>
				<li class="items-in-order-item">
					<h3>NOP3 เหล็กรองน็อต-ชุบดีบุก</h3>
					<div class="ui-grid-a">
						<div class="ui-block-a" style="width:70%">
							<p>SKP-Brass 1 ทองเหลือง-งอ 90 องศา (0.8mm.)_[V-10323]</p>
						</div><!-- .ui-block-a -->
						<div class="ui-block-b" style="width:30%">
							<p class="text-right">61,720 pcs</p>
						</div><!-- .ui-block-b -->
					</div><!-- .ui-grid-a -->
					<div class="action-block">
						<button type="button" class="ui-btn ui-corner-all ui-btn-inline ui-mini btn-success edit-price-btn" data-rel="popup">Edit</button>
						<button type="button" class="ui-btn ui-corner-all ui-btn-inline ui-mini btn-danger delete-btn" data-rel="popup">Delete</button>
					</div>
				</li>
			</ul>
		</div>
		<div id="step-block" data-role="footer" data-position="fixed">
			<a id="prevStep" href="#" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-left ui-icon-carat-l ui-btn-left btn-primary" data-direction="reverse">Prev</a>
			<h4>Step 2 of 2</h4>
			<a  id="save"  href="#" class="ui-btn ui-corner-all ui-btn-inline ui-mini ui-btn-icon-right ui-btn-icon-notext ui-icon-check ui-btn-right btn-success" data-rel="popup">&nbsp;</a>
		</div>
		
		<div data-role="popup" id="editPriceDialog" data-theme="a" data-overlay-theme="b" class="ui-corner-all" data-dismissible="false" style="width:260px;">
			<form action="#" method="get">
				<div style="padding:10px 20px;">
					<label for="price" class="ui-hidden-accessible">Price:</label>
					<input type="text" name="price" id="price" value="1" autocomplete="off" data-mini="true" />
					<a href="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-mini" data-rel="back">Cancel</a>
					<button type="submit" class="ui-btn ui-corner-all ui-btn-inline ui-mini btn-primary">Edit</button>
				</div>
			</form>
		</div>
		
		<div data-role="popup" id="deleteDialog" data-theme="none" data-overlay-theme="b" data-transition="slideup" data-position-to="#step-block" data-dismissible="false" style="width:260px;">
			<div data-role="controlgroup">
				<a href="#" class="ui-btn ui-corner-all ui-mini cancel-txt" data-rel="back" data-transition="flow">Remove Item</a>
				<a href="#" class="ui-btn ui-corner-all ui-mini" data-rel="back">Cancel</a>
			</div>
		</div>
	</div>
	
	<!-- Page Javascript -->
	<script
		src="${pageContext.request.contextPath}/resources/js/stocktaking2.js"></script>
</body>
</html>