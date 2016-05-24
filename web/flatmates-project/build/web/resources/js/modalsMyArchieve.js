
var productID;

$(document).ready(function () {
    $(".todo-item").click(function () {
        productID = $(this).attr('id');
        $(".modal-item").toggle();
        $(".dark-background").toggle();
    });
    $(".flatOnClick").click(function () {
        $(".modal-flat").toggle();
        $(".dark-background").toggle();

    });
    
        $(".changelanguage").click(function () {
         var id = $("#chooseLangage").val();
           window.location.replace(baseURL + "MyArchieve.jsp?lang="+ id); 
    });

    $(".userFilter").click(function () {
        var id = $(this).attr('id');
        window.location.replace(baseURL + "MyArchieve.jsp?userFilter=" + id);
    });


    $(".accountOnClick").click(function () {
        $(".modal-account").toggle();
        $(".dark-background").toggle();
    });
    $(".modal-account-changePassword").click(function () {
        $(".modal-account").toggle();
        $(".modal-account-changePassword-change").toggle();
    });
    $(".modal-account-changeLanguage").click(function () {
        $(".modal-account").toggle();
        $(".modal-account-changeLanguage-change").toggle();
    });
    $(".modal-edit-click").click(function () {
        $(".modal-flat").toggle();
        $(".modal-flat-edit").toggle();
    });
    $(".modal-flat-add").click(function () {
        $(".modal-flat").toggle();
        $(".modal-flat-edit-add").toggle();
    });

    $(".modal-flat-sign").click(function () {
        $(".modal-flat").toggle();
        $(".modal-flat-edit-sign").toggle();
    });

    $(".modal-flat-delete").click(function () {
        $(".modal-flat").toggle();
        $(".modal-flat-edit-delete").toggle();
    });
    $(".modal-flat-edit").click(function () {
        $(".modal-flat").toggle();
        $(".modal-flat-edit-edit-changePassword").toggle();
    });
    $(".dark-background").click(function () {
        $(".myModal").hide();
        $('.dark-background').hide();
    });
    $(".buyNow").click(function () {
        $(".myModal").hide();
        $(".buyNow-modal").toggle();
    });

    $(".information-archieve").click(function () {
        var encodedParam = baseURL + "MyArchieve.jsp?productInfo=" + 4;
        window.location.replace(encodedParam);
    });

    $(".addtoMainList").click(function () {
        var encodedParam = baseURL + "MyArchieve.jsp?productBack=" + productID;
        window.location.replace(encodedParam);
    });

    $(".archieve-item").click(function () {
        productID = $(this).attr('id');
        var encodedParam = baseURL + "MyArchieve.jsp?productOption=" + productID;
        window.location.replace(encodedParam);
        /*
         $(".myModal").hide();
         $(".dark-background").toggle();
         $(".modal-archieve").toggle(); */
    });

    $(".archieve-item-main").click(function () {
        $(".myModal").hide();
        $(".dark-background").toggle();
        $(".information-modal").toggle();
    });

    $(".buyNowWithPrice").click(function () {
        var price = $(".price").val();
        var encodedParam = baseURL + "MyArchieve.jsp?productID=b" + productID + "a" + price;

        window.location.replace(encodedParam);
    });

    $(".makeReservation").click(function () {
        var encodedParam = baseURL + "MyArchieve.jsp?productID=r" + productID;
        window.location.replace(encodedParam);
    });
    /*
     $(".information").click(function () {
     window.location.replace(baseURL + "MyArchieve.jsp?productID=i" + productID);
     });
     */
    $(".doDelete").click(function () {
        window.location.replace(baseURL + "MyArchieve.jsp?productID=d" + productID);
    });
    $(".flatClick").click(function () {

        var id = $(this).attr('id');
        window.location.replace(baseURL + "MyArchieve.jsp?id=" + id);
    });


    $(".clickAll").click(function () {
        window.location.replace(baseURL + "MyArchieve.jsp?filter=all");
    });

    $(".clickActive").click(function () {
        window.location.replace(baseURL + "MyArchieve.jsp?filter=active");
    });

    $(".clickReserve").click(function () {
        var url = encodeURI(baseURL + "MyArchieve.jsp?filter=reserve");
        window.location.replace(url);
    });

    $(".mainlistClick").click(function () {
        var url = encodeURI(baseURL + "MainList.jsp");
        window.location.replace(url);
    });

    $(".mylistClick").click(function () {
        var url = encodeURI(baseURL + "MyList.jsp");
        window.location.replace(url);
    });

    $(".myarchieveClick").click(function () {
        var url = encodeURI(baseURL + "MyArchieve.jsp");
        window.location.replace(url);
    });

    $(".mainarchieveClick").click(function () {
        var url = encodeURI(baseURL + "MainArchieve.jsp");
        window.location.replace(url);
    });


    function logout() {
        window.location.replace(baseURL + "MyArchieve.jsp?logout=logout");
    }

    function addProductMain(e) {
        if (e.keyCode == 13) {
            var productName = $("#new-todo").val();
            window.location.replace(baseURL + "MyArchieve.jsp?productName=" + productName);
            return false;
        }
    }

    $(".nextPage").click(function () {
        var url = encodeURI(baseURL + "MyArchieve.jsp?next=1");
        window.location.replace(url);
    })
    $(".prevPage").click(function () {
        var url = encodeURI(baseURL + "MyArchieve.jsp?prev=1");
        window.location.replace(url);
    })

    $(".modal-flat-viewFlats").click(function () {
        $(".myModal").hide();
        $(".modal-flat-viewFlats-show").toggle();
    });


    $(".modal-flat-viewUsers").click(function () {
        $(".myModal").hide();
        $(".modal-flat-viewUsers-show").toggle();
    });

});