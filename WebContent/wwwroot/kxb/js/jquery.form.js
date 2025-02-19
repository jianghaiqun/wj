!function(a) {
    function b() {
        if (a.fn.ajaxSubmit.debug) {
            var b = "[jquery.form] " + Array.prototype.join.call(arguments, "");
            window.console && window.console.log ? window.console.log(b) :window.opera && window.opera.postError && window.opera.postError(b);
        }
    }
    a.fn.ajaxSubmit = function(c) {
        function d() {
            function d() {
                var b, c, d = k.attr("target"), f = k.attr("action");
                t.setAttribute("target", i), "POST" != t.getAttribute("method") && t.setAttribute("method", "POST"), 
                t.getAttribute("action") != g.url && t.setAttribute("action", g.url), g.skipEncodingOverride || k.attr({
                    encoding:"multipart/form-data",
                    enctype:"multipart/form-data"
                }), g.timeout && setTimeout(function() {
                    p = !0, e();
                }, g.timeout), b = [];
                try {
                    if (g.extraData) for (c in g.extraData) b.push(a('<input type="hidden" name="' + c + '" value="' + g.extraData[c] + '" />').appendTo(t)[0]);
                    j.appendTo("body"), j.data("form-plugin-onload", e), t.submit();
                } finally {
                    t.setAttribute("action", f), d ? t.setAttribute("target", d) :k.removeAttr("target"), 
                    a(b).remove();
                }
            }
            function e() {
                var c, d, h, i, k, q;
                if (!o) {
                    c = !0;
                    try {
                        if (p) throw "timeout";
                        if (h = l.contentWindow ? l.contentWindow.document :l.contentDocument ? l.contentDocument :l.document, 
                        i = "xml" == g.dataType || h.XMLDocument || a.isXMLDoc(h), b("isXml=" + i), !i && (null == h.body || "" == h.body.innerHTML)) return --s ? (b("requeing onLoad callback, DOM not available"), 
                        setTimeout(e, 250), void 0) :(b("Could not access iframe DOM after 100 tries."), 
                        void 0);
                        b("response detected"), o = !0, m.responseText = h.body ? h.body.innerHTML :null, 
                        m.responseXML = h.XMLDocument ? h.XMLDocument :h, m.getResponseHeader = function(a) {
                            var b = {
                                "content-type":g.dataType
                            };
                            return b[a];
                        }, "json" == g.dataType || "script" == g.dataType ? (k = h.getElementsByTagName("textarea")[0], 
                        k ? m.responseText = k.value :(q = h.getElementsByTagName("pre")[0], q && (m.responseText = q.innerHTML))) :"xml" != g.dataType || m.responseXML || null == m.responseText || (m.responseXML = f(m.responseText)), 
                        d = a.httpData(m, g.dataType);
                    } catch (r) {
                        b("error caught:", r), c = !1, m.error = r, a.handleError(g, m, "error", r);
                    }
                    c && (g.success(d, "success"), n && a.event.trigger("ajaxSuccess", [ m, g ])), n && a.event.trigger("ajaxComplete", [ m, g ]), 
                    n && !--a.active && a.event.trigger("ajaxStop"), g.complete && g.complete(m, c ? "success" :"error"), 
                    setTimeout(function() {
                        j.removeData("form-plugin-onload"), j.remove(), m.responseXML = null;
                    }, 100);
                }
            }
            function f(a, b) {
                return window.ActiveXObject ? (b = new ActiveXObject("Microsoft.XMLDOM"), b.async = "false", 
                b.loadXML(a)) :b = new DOMParser().parseFromString(a, "text/xml"), b && b.documentElement && "parsererror" != b.documentElement.tagName ? b :null;
            }
            var g, h, i, j, l, m, n, o, p, q, r, s, t = k[0];
            return a(":input[name=submit]", t).length ? (alert('Error: Form elements must not be named "submit".'), 
            void 0) :(g = a.extend({}, a.ajaxSettings, c), h = a.extend(!0, {}, a.extend(!0, {}, a.ajaxSettings), g), 
            i = "jqFormIO" + new Date().getTime(), j = a('<iframe id="' + i + '" name="' + i + '" src="' + g.iframeSrc + '" onload="(jQuery(this).data(\'form-plugin-onload\'))()" />'), 
            l = j[0], j.css({
                position:"absolute",
                top:"-1000px",
                left:"-1000px"
            }), m = {
                aborted:0,
                responseText:null,
                responseXML:null,
                status:0,
                statusText:"n/a",
                getAllResponseHeaders:function() {},
                getResponseHeader:function() {},
                setRequestHeader:function() {},
                abort:function() {
                    this.aborted = 1, j.attr("src", g.iframeSrc);
                }
            }, n = g.global, n && !a.active++ && a.event.trigger("ajaxStart"), n && a.event.trigger("ajaxSend", [ m, g ]), 
            h.beforeSend && h.beforeSend(m, h) === !1 ? (h.global && a.active--, void 0) :(m.aborted || (o = !1, 
            p = 0, q = t.clk, q && (r = q.name, r && !q.disabled && (g.extraData = g.extraData || {}, 
            g.extraData[r] = q.value, "image" == q.type && (g.extraData[r + ".x"] = t.clk_x, 
            g.extraData[r + ".y"] = t.clk_y))), g.forceSync ? d() :setTimeout(d, 10), s = 100), 
            void 0));
        }
        var e, f, g, h, i, j, k, l, m, n, o, p, q;
        if (!this.length) return b("ajaxSubmit: skipping submit process - no element selected"), 
        this;
        if ("function" == typeof c && (c = {
            success:c
        }), e = a.trim(this.attr("action")), e && (e = (e.match(/^([^#]+)/) || [])[1]), 
        e = e || window.location.href || "", c = a.extend({
            url:e,
            type:this.attr("method") || "GET",
            iframeSrc:/^https/i.test(window.location.href || "") ? "javascript:false" :"about:blank"
        }, c || {}), f = {}, this.trigger("form-pre-serialize", [ this, c, f ]), f.veto) return b("ajaxSubmit: submit vetoed via form-pre-serialize trigger"), 
        this;
        if (c.beforeSerialize && c.beforeSerialize(this, c) === !1) return b("ajaxSubmit: submit aborted via beforeSerialize callback"), 
        this;
        if (g = this.formToArray(c.semantic), c.data) {
            c.extraData = c.data;
            for (h in c.data) if (c.data[h] instanceof Array) for (i in c.data[h]) g.push({
                name:h,
                value:c.data[h][i]
            }); else g.push({
                name:h,
                value:c.data[h]
            });
        }
        if (c.beforeSubmit && c.beforeSubmit(g, this, c) === !1) return b("ajaxSubmit: submit aborted via beforeSubmit callback"), 
        this;
        if (this.trigger("form-submit-validate", [ g, this, c, f ]), f.veto) return b("ajaxSubmit: submit vetoed via form-submit-validate trigger"), 
        this;
        for (j = a.param(g), "GET" == c.type.toUpperCase() ? (c.url += (c.url.indexOf("?") >= 0 ? "&" :"?") + j, 
        c.data = null) :c.data = j, k = this, l = [], c.resetForm && l.push(function() {
            k.resetForm();
        }), c.clearForm && l.push(function() {
            k.clearForm();
        }), !c.dataType && c.target ? (m = c.success || function() {}, l.push(function(b) {
            var d = c.replaceTarget ? "replaceWith" :"html";
            a(c.target)[d](b).each(m, arguments);
        })) :c.success && l.push(c.success), c.success = function(a, b, d) {
            for (var e = 0, f = l.length; f > e; e++) l[e].apply(c, [ a, b, d || k, k ]);
        }, n = a("input:file", this).fieldValue(), o = !1, p = 0; p < n.length; p++) n[p] && (o = !0);
        return q = !1, n.length && c.iframe !== !1 || c.iframe || o || q ? c.closeKeepAlive ? a.get(c.closeKeepAlive, d) :d() :a.ajax(c), 
        this.trigger("form-submit-notify", [ this, c ]), this;
    }, a.fn.ajaxForm = function(b) {
        return this.ajaxFormUnbind().bind("submit.form-plugin", function(c) {
            c.preventDefault(), a(this).ajaxSubmit(b);
        }).bind("click.form-plugin", function(b) {
            var c, d, e, f = b.target, g = a(f);
            if (!g.is(":submit,input:image")) {
                if (c = g.closest(":submit"), 0 == c.length) return;
                f = c[0];
            }
            d = this, d.clk = f, "image" == f.type && (void 0 != b.offsetX ? (d.clk_x = b.offsetX, 
            d.clk_y = b.offsetY) :"function" == typeof a.fn.offset ? (e = g.offset(), d.clk_x = b.pageX - e.left, 
            d.clk_y = b.pageY - e.top) :(d.clk_x = b.pageX - f.offsetLeft, d.clk_y = b.pageY - f.offsetTop)), 
            setTimeout(function() {
                d.clk = d.clk_x = d.clk_y = null;
            }, 100);
        });
    }, a.fn.ajaxFormUnbind = function() {
        return this.unbind("submit.form-plugin click.form-plugin");
    }, a.fn.formToArray = function(b) {
        var c, d, e, f, g, h, i, j, k, l, m, n = [];
        if (0 == this.length) return n;
        if (c = this[0], d = b ? c.getElementsByTagName("*") :c.elements, !d) return n;
        for (e = 0, f = d.length; f > e; e++) if (g = d[e], h = g.name) if (b && c.clk && "image" == g.type) g.disabled || c.clk != g || (n.push({
            name:h,
            value:a(g).val()
        }), n.push({
            name:h + ".x",
            value:c.clk_x
        }, {
            name:h + ".y",
            value:c.clk_y
        })); else if (i = a.fieldValue(g, !0), i && i.constructor == Array) for (j = 0, 
        k = i.length; k > j; j++) n.push({
            name:h,
            value:i[j]
        }); else null !== i && "undefined" != typeof i && n.push({
            name:h,
            value:i
        });
        return !b && c.clk && (l = a(c.clk), m = l[0], h = m.name, h && !m.disabled && "image" == m.type && (n.push({
            name:h,
            value:l.val()
        }), n.push({
            name:h + ".x",
            value:c.clk_x
        }, {
            name:h + ".y",
            value:c.clk_y
        }))), n;
    }, a.fn.formSerialize = function(b) {
        return a.param(this.formToArray(b));
    }, a.fn.fieldSerialize = function(b) {
        var c = [];
        return this.each(function() {
            var d, e, f, g = this.name;
            if (g) if (d = a.fieldValue(this, b), d && d.constructor == Array) for (e = 0, f = d.length; f > e; e++) c.push({
                name:g,
                value:d[e]
            }); else null !== d && "undefined" != typeof d && c.push({
                name:this.name,
                value:d
            });
        }), a.param(c);
    }, a.fn.fieldValue = function(b) {
        var c, d, e, f, g;
        for (c = [], d = 0, e = this.length; e > d; d++) f = this[d], g = a.fieldValue(f, b), 
        null === g || "undefined" == typeof g || g.constructor == Array && !g.length || (g.constructor == Array ? a.merge(c, g) :c.push(g));
        return c;
    }, a.fieldValue = function(a, b) {
        var c, d, e, f, g, h, i, j, k = a.name, l = a.type, m = a.tagName.toLowerCase();
        if ("undefined" == typeof b && (b = !0), b && (!k || a.disabled || "reset" == l || "button" == l || ("checkbox" == l || "radio" == l) && !a.checked || ("submit" == l || "image" == l) && a.form && a.form.clk != a || "select" == m && -1 == a.selectedIndex)) return null;
        if ("select" == m) {
            if (c = a.selectedIndex, 0 > c) return null;
            for (d = [], e = a.options, f = "select-one" == l, g = f ? c + 1 :e.length, h = f ? c :0; g > h; h++) if (i = e[h], 
            i.selected) {
                if (j = i.value, j || (j = i.attributes && i.attributes.value && !i.attributes.value.specified ? i.text :i.value), 
                f) return j;
                d.push(j);
            }
            return d;
        }
        return a.value;
    }, a.fn.clearForm = function() {
        return this.each(function() {
            a("input,select,textarea", this).clearFields();
        });
    }, a.fn.clearFields = a.fn.clearInputs = function() {
        return this.each(function() {
            var a = this.type, b = this.tagName.toLowerCase();
            "text" == a || "password" == a || "textarea" == b ? this.value = "" :"checkbox" == a || "radio" == a ? this.checked = !1 :"select" == b && (this.selectedIndex = -1);
        });
    }, a.fn.resetForm = function() {
        return this.each(function() {
            ("function" == typeof this.reset || "object" == typeof this.reset && !this.reset.nodeType) && this.reset();
        });
    }, a.fn.enable = function(a) {
        return void 0 == a && (a = !0), this.each(function() {
            this.disabled = !a;
        });
    }, a.fn.selected = function(b) {
        return void 0 == b && (b = !0), this.each(function() {
            var c, d = this.type;
            "checkbox" == d || "radio" == d ? this.checked = b :"option" == this.tagName.toLowerCase() && (c = a(this).parent("select"), 
            b && c[0] && "select-one" == c[0].type && c.find("option").selected(!1), this.selected = b);
        });
    };
}(jQuery);