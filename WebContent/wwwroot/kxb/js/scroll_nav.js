(function(jQuery_1_7_2){

  var settings = {
        speed: 100
      , easing: "linear" 
      , padding: 0
      , constrain: false
    }
    , jQuery_1_7_2window = jQuery_1_7_2(window)
    , stickyboxes = []
    , methods = {

          init:function(opts){
            settings = jQuery_1_7_2.extend(settings,opts);
            return this.each(function () {
              var jQuery_1_7_2this = jQuery_1_7_2(this);
              setPosition(jQuery_1_7_2this);
              stickyboxes[stickyboxes.length] = jQuery_1_7_2this;
              moveIntoView();
            });
          }

        , remove:function(){
            return this.each(function () {
              var sticky = this;
              jQuery_1_7_2.each(stickyboxes, function (i, jQuery_1_7_2sb) {
                if(jQuery_1_7_2sb.get(0) === sticky){
                reset(null, jQuery_1_7_2sb);
                stickyboxes.splice(i, 1);
                return false;
                }
              });
            });
          }

        , destroy: function () {
            jQuery_1_7_2.each(stickyboxes, function (i, jQuery_1_7_2sb) {
              reset(null, jQuery_1_7_2sb);
            });
            stickyboxes=[];
            jQuery_1_7_2window.unbind("scroll", moveIntoView);
            jQuery_1_7_2window.unbind("resize", reset);
            return this;
          }

      };


  var moveIntoView = function () {
    jQuery_1_7_2.each(stickyboxes, function (i, jQuery_1_7_2sb) {
      var jQuery_1_7_2this = jQuery_1_7_2sb
        , data = jQuery_1_7_2this.data("stickySB");
      if (data) {
        var sTop = jQuery_1_7_2window.scrollTop() - data.offs.top
          , currOffs = jQuery_1_7_2this.offset()
          , origTop = data.orig.offset.top - data.offs.top
          , animTo = origTop;
        //scrolled down out of view
        if (origTop < sTop) {
					//make sure to stop inside parent
          if ((sTop + settings.padding) > data.offs.bottom)
            animTo = data.offs.bottom;
          else animTo = sTop + settings.padding;
        }
        jQuery_1_7_2this
          .stop()
          .animate(
              {top: animTo}
            , settings.speed
            , settings.easing
        );
      }
    });
  };

  var setPosition = function (jQuery_1_7_2sb) {
    if (jQuery_1_7_2sb) {
      var jQuery_1_7_2this = jQuery_1_7_2sb
        , jQuery_1_7_2parent = jQuery_1_7_2this.parent()
        , parentOffs = jQuery_1_7_2parent.offset()
        , currOff = jQuery_1_7_2this.offset()
        , data = jQuery_1_7_2this.data("stickySB");
      if (!data) {
        data = {
            offs: {} // our parents offset
          , orig: { // cache for original css
                top: jQuery_1_7_2this.css("top")
              , left: jQuery_1_7_2this.css("left")
              , position: jQuery_1_7_2this.css("position")
              , marginTop: jQuery_1_7_2this.css("marginTop")
              , marginLeft: jQuery_1_7_2this.css("marginLeft")
              , offset: jQuery_1_7_2this.offset()
            }
        };
      }
      //go up the tree until we find an elem to position from
      while (parentOffs && "top" in parentOffs
        && jQuery_1_7_2parent.css("position") == "static") {
        jQuery_1_7_2parent = jQuery_1_7_2parent.parent();
        parentOffs = jQuery_1_7_2parent.offset();
      }
      if (parentOffs) { // found a postioned ancestor
        var padBtm = parseInt(jQuery_1_7_2parent.css("paddingBottom"));
        padBtm = isNaN(padBtm) ? 0 : padBtm;
        data.offs = parentOffs;
        data.offs.bottom = settings.constrain ?
          Math.abs((jQuery_1_7_2parent.innerHeight() - padBtm) - jQuery_1_7_2this.outerHeight()) :
          jQuery_1_7_2(document).height();
      }
      else data.offs = { // went to far set to doc
          top: 0
        , left: 0
        , bottom: jQuery_1_7_2(document).height()
      };
      jQuery_1_7_2this.css({
          position: "absolute"
        , top: Math.floor(currOff.top - data.offs.top) + "px"
        , left: Math.floor(currOff.left - data.offs.left) + "px"
        , margin: 0
        , width: jQuery_1_7_2this.width()
      }).data("stickySB", data);
    }
  };

  var reset = function (ev, jQuery_1_7_2toReset) {
    var stickies = stickyboxes;
    if (jQuery_1_7_2toReset) { // just resetting selected items
      stickies = [jQuery_1_7_2toReset];
    }
    jQuery_1_7_2.each(stickies, function(i, jQuery_1_7_2sb) {
      var data = jQuery_1_7_2sb.data("stickySB");
      if (data) {
        jQuery_1_7_2sb.css({
            position: data.orig.position
          , marginTop: data.orig.marginTop
          , marginLeft: data.orig.marginLeft
          , left: data.orig.left
          , top: data.orig.top
        });
        if (!jQuery_1_7_2toReset) { // just resetting
          setPosition(jQuery_1_7_2sb);
          moveIntoView();
        }
      }
    });
  };

  jQuery_1_7_2window.bind("scroll", moveIntoView);
  jQuery_1_7_2window.bind("resize", reset);

  jQuery_1_7_2.fn.stickySidebar = function (method) {

    if (methods[method]) {
      return methods[method].apply(
          this
        , Array.prototype.slice.call(arguments, 1)
      );
    } else if (!method || typeof method == "object") {
      return methods.init.apply(this, arguments);
    }

  };

})(jQuery_1_7_2);