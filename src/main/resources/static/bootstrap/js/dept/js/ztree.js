		var lastValue = "", nodeList = [], key = $("#keyword");
		key.bind("focus", focusKey).bind("blur", blurKey).bind("change cut input propertychange", searchNode);
		key.bind("keydown", function (e){if(e.which == 13){searchNode();}});
	
		function focusKey(e) {
			if (key.hasClass("empty")) {
				key.removeClass("empty");
			}
		}
	
		function blurKey(e) {
			if (key.get(0).value === "") {
				key.addClass("empty");
			}
			searchNode(e);
		}
	
		function searchNode() {
			var value = $.trim(key.get(0).value);
			var keyType = "name";
			if (lastValue === value) {
				return;
			}
			lastValue = value;
			var nodes = ztree.getNodes();
			if (value == "") {
				showAllNode(nodes);
				return;
			}
			hideAllNode(nodes);
			nodeList = ztree.getNodesByParamFuzzy(keyType, value);
			updateNodes(nodeList);
		}
	
		function hideAllNode(nodes){
			//var ztree = $.fn.zTree.getZTreeObj("deptTree");
			nodes = ztree.transformToArray(nodes);
			for(var i=nodes.length-1; i>=0; i--) {
				ztree.hideNode(nodes[i]);
			}
		}
	
		function showAllNode(nodes){
			nodes = ztree.transformToArray(nodes);
			for(var i=nodes.length-1; i>=0; i--) {
				if(nodes[i].getParentNode()!=null){
					ztree.expandNode(nodes[i],false,false,false,false);
				}else{
					ztree.expandNode(nodes[i],true,true,false,false);
				}
				ztree.showNode(nodes[i]);
				showAllNode(nodes[i].children);
			}
		}
	
		function updateNodes(nodeList) {
			ztree.showNodes(nodeList);
			for(var i=0, l=nodeList.length; i<l; i++) {
				var treeNode = nodeList[i];
				showChildren(treeNode);
				showParent(treeNode)
			}
		}
	
		function showChildren(treeNode){
			if (treeNode.isParent){
				for(var idx in treeNode.children){
					var node = treeNode.children[idx];
					ztree.showNode(node);
					showChildren(node);
				}
			}
		}
		function showParent(treeNode){
			var parentNode;
			while((parentNode = treeNode.getParentNode()) != null){
				ztree.showNode(parentNode);
				ztree.expandNode(parentNode, true, false, false);
				treeNode = parentNode;
			}
		}
	
		function search($this) {
			$('#search').slideToggle(200);
			$('#btnShow').toggle();
			$('#btnHide').toggle();
			$('#keyword').focus();
		}
		
$(function () {
		    
		    
		    $('#btnExpand').click(function() {
				ztree.expandAll(true);
			});
			$('#btnCollapse').click(function() {
				ztree.expandAll(false);
			});
		});