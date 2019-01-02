var lastValue = "", nodeList = [], key = $("#keyword");
key.bind("focus", focusKey).bind("blur", blurKey).bind("change cut input propertychange", searchNode);
key.bind("keydown", function (e) {
    if (e.which == 13) {
        searchNode();
    }
});

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
    var keyType = "rname";
    if (lastValue === value) {

        return;
    }
    lastValue = value;
    var nodes = Roleztree.getNodes();
    if (value == "") {
        showRoleAllNode(nodes);
        return;
    }
    hideRoleAllNode(nodes);
    nodeList = Roleztree.getNodesByParamFuzzy(keyType, value);

    updateRoleNodes(nodeList);
}

function hideRoleAllNode(nodes) {
    //var ztree = $.fn.zTree.getZTreeObj("deptTree");
    nodes = Roleztree.transformToArray(nodes);
    for (var i = nodes.length - 1; i >= 0; i--) {
        Roleztree.hideNode(nodes[i]);
    }
}

function showRoleAllNode(nodes) {

    nodes = Roleztree.transformToArray(nodes);
    for (var i = nodes.length - 1; i >= 0; i--) {
        if (nodes[i].getParentNode() != null) {
            Roleztree.expandNode(nodes[i], false, false, false, false);
        } else {
            Roleztree.expandNode(nodes[i], true, true, false, false);
        }
        Roleztree.showNode(nodes[i]);
        showRoleAllNode(nodes[i].children);
    }
}

function updateRoleNodes(nodeList) {

    Roleztree.showNodes(nodeList);
    for (var i = 0, l = nodeList.length; i < l; i++) {
        var treeNode = nodeList[i];
        showRoleChildren(treeNode);
        showRoleParent(treeNode)
    }
}

function showRoleChildren(treeNode) {

    if (treeNode.isParent) {
        for (var idx in treeNode.children) {
            var node = treeNode.children[idx];
            Roleztree.showNode(node);
            showRoleChildren(node);
        }
    }
}
function showRoleParent(treeNode) {

    var parentNode;
    while ((parentNode = treeNode.getParentNode()) != null) {
        Roleztree.showNode(parentNode);
        Roleztree.expandNode(parentNode, true, false, false);
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
    $('#btnExpand').click(function () {
        Roleztree.expandAll(true);
    });
    $('#btnCollapse').click(function () {
        Roleztree.expandAll(false);
    });
});