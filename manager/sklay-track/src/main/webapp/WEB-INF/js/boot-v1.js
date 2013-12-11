(function () {
    if (_has(typeof Atk)) {
        return;
    }

    if (typeof JSON !== 'object') {
        JSON = {};
    }
    (function () {
        'use strict';
        function f(n) {
            return n < 10 ? '0' + n : n;
        }

        if (typeof Date.prototype.toJSON !== 'function') {
            Date.prototype.toJSON = function (key) {
                return isFinite(this.valueOf())
                    ? this.getUTCFullYear() + '-' +
                    f(this.getUTCMonth() + 1) + '-' +
                    f(this.getUTCDate()) + 'T' +
                    f(this.getUTCHours()) + ':' +
                    f(this.getUTCMinutes()) + ':' +
                    f(this.getUTCSeconds()) + 'Z'
                    : null;
            };
            String.prototype.toJSON =
                Number.prototype.toJSON =
                    Boolean.prototype.toJSON = function (key) {
                        return this.valueOf();
                    };
        }
        var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
            escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
            gap,
            indent,
            meta = {
                '\b': '\\b',
                '\t': '\\t',
                '\n': '\\n',
                '\f': '\\f',
                '\r': '\\r',
                '"': '\\"',
                '\\': '\\\\'
            },
            rep;

        function quote(string) {
            escapable.lastIndex = 0;
            return escapable.test(string) ? '"' + string.replace(escapable, function (a) {
                var c = meta[a];
                return typeof c === 'string'
                    ? c
                    : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
            }) + '"' : '"' + string + '"';
        }

        function str(key, holder) {
            var i,
                k,
                v,
                length,
                mind = gap,
                partial,
                value = holder[key];
            if (value && typeof value === 'object' &&
                typeof value.toJSON === 'function') {
                value = value.toJSON(key);
            }
            if (typeof rep === 'function') {
                value = rep.call(holder, key, value);
            }
            switch (typeof value) {
                case 'string':
                    return quote(value);
                case 'number':
                    return isFinite(value) ? String(value) : 'null';
                case 'boolean':
                case 'null':
                    return String(value);
                case 'object':
                    if (!value) {
                        return 'null';
                    }
                    gap += indent;
                    partial = [];
                    if (Object.prototype.toString.apply(value) === '[object Array]') {
                        length = value.length;
                        for (i = 0; i < length; i += 1) {
                            partial[i] = str(i, value) || 'null';
                        }
                        v = partial.length === 0
                            ? '[]'
                            : gap
                            ? '[\n' + gap + partial.join(',\n' + gap) + '\n' + mind + ']'
                            : '[' + partial.join(',') + ']';
                        gap = mind;
                        return v;
                    }
                    if (rep && typeof rep === 'object') {
                        length = rep.length;
                        for (i = 0; i < length; i += 1) {
                            if (typeof rep[i] === 'string') {
                                k = rep[i];
                                v = str(k, value);
                                if (v) {
                                    partial.push(quote(k) + (gap ? ': ' : ':') + v);
                                }
                            }
                        }
                    } else {
                        for (k in value) {
                            if (Object.prototype.hasOwnProperty.call(value, k)) {
                                v = str(k, value);
                                if (v) {
                                    partial.push(quote(k) + (gap ? ': ' : ':') + v);
                                }
                            }
                        }
                    }
                    v = partial.length === 0
                        ? '{}'
                        : gap
                        ? '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}'
                        : '{' + partial.join(',') + '}';
                    gap = mind;
                    return v;
            }
        }

        if (typeof JSON.stringify !== 'function') {
            JSON.stringify = function (value, replacer, space) {
                var i;
                gap = '';
                indent = '';
                if (typeof space === 'number') {
                    for (i = 0; i < space; i += 1) {
                        indent += ' ';
                    }
                } else if (typeof space === 'string') {
                    indent = space;
                }
                rep = replacer;
                if (replacer && typeof replacer !== 'function' &&
                    (typeof replacer !== 'object' ||
                        typeof replacer.length !== 'number')) {
                    throw new Error('JSON.stringify');
                }
                return str('', {'': value});
            };
        }
        if (typeof JSON.parse !== 'function') {
            JSON.parse = function (text, reviver) {
                var j;

                function walk(holder, key) {
                    var k, v, value = holder[key];
                    if (value && typeof value === 'object') {
                        for (k in value) {
                            if (Object.prototype.hasOwnProperty.call(value, k)) {
                                v = walk(value, k);
                                if (v !== undefined) {
                                    value[k] = v;
                                } else {
                                    delete value[k];
                                }
                            }
                        }
                    }
                    return reviver.call(holder, key, value);
                }

                text = String(text);
                cx.lastIndex = 0;
                if (cx.test(text)) {
                    text = text.replace(cx, function (a) {
                        return '\\u' +
                            ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
                    });
                }
                if (/^[\],:{}\s]*$/
                    .test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@')
                        .replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
                        .replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {
                    j = eval('(' + text + ')');
                    return typeof reviver === 'function'
                        ? walk({'': j}, '')
                        : j;
                }
                throw new SyntaxError('JSON.parse');
            };
        }
    }());

    var _domReady = function (ready) {
        var fns = [], fn, f = false,
            doc = document,
            testEl = doc.documentElement,
            hack = testEl.doScroll,
            domContentLoaded = 'DOMContentLoaded',
            addEventListener = 'addEventListener',
            onreadystatechange = 'onreadystatechange',
            readyState = 'readyState',
            loaded = /^loade|c/.test(doc[readyState]);

        function flush(f) {
            loaded = 1;
            while (f = fns.shift()) f();
        }

        doc[addEventListener] && doc[addEventListener](domContentLoaded, fn = function () {
            doc.removeEventListener(domContentLoaded, fn, f);
            flush();
        }, f);

        hack && doc.attachEvent(onreadystatechange, fn = function () {
            if (/^c/.test(doc[readyState])) {
                doc.detachEvent(onreadystatechange, fn);
                flush();
            }
        });

        return (ready = hack ?
            function (fn) {
                self != top ?
                    loaded ? fn() : fns.push(fn) :
                    function () {
                        try {
                            testEl.doScroll('left');
                        } catch (e) {
                            return setTimeout(function () {
                                ready(fn);
                            }, 50);
                        }
                        fn();
                    }();
            } :
            function (fn) {
                loaded ? fn() : fns.push(fn);
            });
    }();

    function _has(type) {
        return type !== 'undefined';
    }

    function _isFalse(v) {
        return v == false || v == 'false';
    }

    function _apply(object, config, defaults, skipExist) {
        defaults && _apply(object, defaults);
        if (object && config && typeof config === 'object') {
            for (var p in config) {
                config.hasOwnProperty(p) && (!skipExist || object[p] === undefined) && (object[p] = config[p]);
            }
        }
        return object;
    }

    function _each(arr, fn, scope) {
        if (!arr) {
            return false;
        }
        var i, ln = arr.length;
        for (i = 0; i < ln; i++) {
            if (fn.call(scope || arr[i], arr[i], i, arr) === false) {
                return i;
            }
        }
        return true;
    }

    function _fixUrl(url) {
        if (url.indexOf('http') == -1) {
            return location.href.match(/([^?]*\/)/)[1] + url;
        } else if (url.indexOf('/') == 0) {
            return location.href.match(/(.*\/\/[^/]*)\//)[1] + url;
        }
        return url;
    }

    function _isInDomain(url, domain) {
        if (url && domain) {
            var i = url.indexOf(domain);
            var j = url.indexOf('/', 8);
            return i > -1 && (i < j || j == -1);
        }
        return false;
    }

    Atk = function (cfs) {
        this._cfs = cfs || {};//配置
        this._customs = {};//event自定义数据
        this._actions = [];//事件
        this._errors = [];//错误信息
    };
    var _ua = navigator.userAgent;
    //var _chrome = /chrome/i.test(_ua);
    Atk.prototype = {
        _UA: navigator.userAgent,
        _CHARS: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_'.split(''),
        _EVENTS: {BEGIN: 0, END: 1, HEARTBEAT: 2},
        _ACTIONS: {CLICK: 0, INNNER_CLICK: 1, OUTER_CLICK: 2, INPUT: 3, AJAX: 4, CUSTOM: 100},
        _BROWSER: {
            //version: (_ua.match(/.+(?:rv|it|ra|ie)[\/: ]([\d.]+)/i) || [])[1],
            //chrome: _chrome,
            //safari: /webkit/i.test(navigator.userAgent) && !_chrome,
            opera: /opera/i.test(_ua),
            //mozilla: /mozilla/i.test(_ua) && !/(compatible|webkit)/i.test(_ua),
            msie: /msie/i.test(_ua) && !/opera/i.test(_ua)
        },
        uuid: function (len) {
            var arr = [];
            len = len || 12;
            for (var i = 0; i < len; i++) {
                arr.push(this._CHARS[Math.floor(Math.random() * 64)]);
            }
            return arr.join('');
        },
        log: function (msg, src) {
            if (this._cfs.debug) {
                typeof msg === 'object' && (msg = JSON.stringify(msg));
                src && (msg = src + ': ' + msg);
                var now = new Date(), ts = now.getHours() + ':' + now.getMinutes() + ':' + now.getSeconds() + '.' + now.getMilliseconds();
                window.console !== undefined && console.log && console.log(ts + ' | ' + msg);
            }
        },
        getTime: function () {
            return new Date().getTime();
        },
        getCookie: function (name) {
            var ret, m;
            if (m = document.cookie.match('(?:^| )' + name + '(?:(?:=([^;]*))|;|$)')) {
                ret = m[1] ? decodeURIComponent(m[1]) : '';
            }
            return ret;
        },
        setCookie: function (name, value, expires, domain, path, secure) {
            var text = encodeURIComponent(value), date = expires;
            if (typeof date === 'number') {
                date = new Date();
                date.setTime(date.getTime() + expires * 1000);
            }
            date instanceof Date && (text += '; expires=' + date.toUTCString());
            domain && (text += '; domain=' + domain);
            path && (text += '; path=' + path);
            secure && (text += '; secure');
            text = name + '=' + text;
            document.cookie = text;
            this.log(text, "Set cookie");
        },
        removeCookie: function (name) {
            this.setCookie(name, '', 0);
        },
        bindEvent: function (el, event, fn) {
            return el.attachEvent ? el.attachEvent('on' + event, fn) : el.addEventListener(event, fn);
        },
        isInDomain: function (url) {
            var domain = this._cfs.sDomain;
            if (domain) {
                if (domain.indexOf('.') == 0) {
                    domain = domain.substr(1);
                }
            } else {
                domain = window.location.host;
            }
            return _isInDomain(url, domain);
        },
        setDomainCookie: function (name, value, expires, path, secure) {
            this.setCookie(name, value, expires, this._cfs.sDomain, path, secure);
            if (this._cfs.sDomain && this.getCookie(name) != value) {
                this.log('Can not read cookie from ' + this._cfs.sDomain + ',is session domain incorrect?', "Error");
                this.setCookie(name, value, expires, null, path, secure);
            }
        },
        trackCustoms: function (data) {
            _apply(this._customs, data);
            this.log(data, "Add custom");
        },
        trackAction: function (action) {
            if (this._actionCallbacks) {
                for (var i = 0, len = this._actionCallbacks.length; i < len; i++) {
                    if (this._actionCallbacks[i](action, action.t) === false) {
                        return;
                    }
                }
            }
            this._actions.length > 10 && this.send(this._EVENTS.HEARTBEAT);
            action.s = this.getTime();
            this._actions.push(action);
            this.log(action, "Add action");
        },
        trackAjax: function (action) {
            action.url = _fixUrl(action.url);
            action.t = this._ACTIONS.AJAX;
            this.trackAction(action);
        },
        trackEx: function (e) {
            var msg;
            if (typeof e === 'string') {
                msg = e;
            } else if (e.name) {
                msg = e.name + ': ' + e.message;
            } else if (e.message) {
                msg = e.message;
            } else if (e.stack) {
                msg = e.stack;
            } else if (e.stacktrace) {
                msg = e.stacktrace;
            }
            this._trackError({t: this.getTime(), msg: msg});
        },
        _trackError: function (error) {
            for (var i = 0; i < this._errors.length; i++) {//排除重复错误信息
                if (this._errors[i].msg == error.msg) {
                    return;
                }
            }
            this._errors.length > 10 && this.send(this._EVENTS.HEARTBEAT);
            this._errors.push(error);
            this.log(error, "Add Error");
        },
        _getEvent: function () {
            var referer = document.referrer, event = {
                url: location.href.replace(/#.+$/, ''),
                title: document.title,
                referer: document.referrer
            };
            if (this.isInDomain(referer)) {
                var fromEventId = this.getCookie('ak_e');
                fromEventId && (event.fei = fromEventId);
            }
            this._tags && (event.tags = this._tags);
            var now = this.getTime();
            this._start && (event.clt = now - (this._start instanceof Date ? this._start.getTime() : this._start));
            this._slt && (event.slt = this._slt);
            if (_has(typeof performance)) {//html5
                if (performance.timing) {
                    event.lt = now - performance.timing.navigationStart;
                    event.clt = now - performance.timing.responseStart;
                }
                performance.memory && (event.mem = Math.floor(performance.memory.totalJSHeapSize / 1000));
            }

            var userId = this.getCookie(this._cfs.uidKey || 'ak_u');
            userId && (event.ui = userId);

            var appSessionId = this.getCookie(this._cfs.sidKey || 'any123_ut');
            appSessionId && (event.asi = appSessionId);

            return event;
        },
        _getClient: function () {
            return {
                screen: screen.width + 'x' + screen.height,
                colorDepth: screen.colorDepth,
                agent: navigator.userAgent,
                lang: navigator.language
            };
        },
        _getSession: function () {
            return {};
        },
        send: function (type, usePost) {
            var data = {t: type};

            if (!this._sessionId) {
                this._sessionId = this._cfs.sessionId;
                if (!this._sessionId) {//如果没有配置sessionId,则读取cookie
                    this._sessionId = this.getCookie('ak_s');
                    if (!this._sessionId) {//如果cookie中没有,则创建新session
                        this._sessionId = this.uuid();
                        this.log(this._sessionId, 'Create new sessionId');
                        data.session = this._getSession();
                    }
                    this.setDomainCookie('ak_s', this._sessionId, this._cfs.sTimeout || 1800, '/');
                }
            }
            data.si = this._sessionId;

            if (!this._clientId) {
                if (this._cfs.tracked) {
                    this._clientId = this._cfs.clientId;
                } else {
                    this._clientId = this.getCookie('ak_c');
                    if (!this._clientId) {
                        this._clientId = this._cfs.clientId;
                        if (!this._clientId) {
                            this._clientId = this._sessionId;
                            this.log(this._clientId, 'Create new clientId');
                        }
                        this.setDomainCookie('ak_c', this._clientId, 3e8, '/');
                    }
                    data.client = this._getClient();
                }
            }

            switch (type) {
                case this._EVENTS.BEGIN:
                    data.ci = this._clientId;
                    break;
                case this._EVENTS.END:
                    var idle = this._updateIdle(true);
                    idle && (data.idle = idle);
                    break;
            }

            if (!this._eventId) {
                this._eventId = this.uuid();
                this.log(this._eventId, 'Create new eventId');
                data.e = this._getEvent();
            }
            data.ei = this._eventId;

            var hasCustom = false;
            for (var p in this._customs) {
                if (this._customs.hasOwnProperty(p)) {
                    hasCustom = true;
                }
            }
            if (hasCustom) {
                data.cs = this._customs;
                this._customs = {};
            }
            if (this._errors.length) {
                data.es = this._errors;
                this._errors = [];
            }
            var now = this.getTime();
            if (this._actions.length) {
                _each(this._actions, function () {
                    this.s && (this.s = Math.floor((now - this.s) / 1e3));
                });
                data.as = this._actions;
                this._actions = [];
            }

            var self = this, json = JSON.stringify(data);
            this.log(json, "Send track data");
            if (usePost) {
                this._post(this._cfs.url + '/track/' + this._cfs.appId + '?s=' + now, json, function () {
                    self._lastSend = self.getTime();
                    self.log("Post complete");
                });
            } else {
                this._get(this._cfs.url + '/track/' + this._cfs.appId + '?d=' + encodeURIComponent(json) + '&s=' + now, function (e) {
                    e == 'onload' && (self._lastSend = self.getTime());
                    self.log("Get complete");
                });
            }
        },
        _get: function (url, callback) {
            var img = new Image(), imgId = 'atk_' + this.uuid(5);
            window[imgId] = img;
            var clean = function (e) {
                img.onload = img.onerror = img.onabort = null;
                window[imgId] = null;
                img = null;
                callback && callback(e, url);
            };
            img.onload = function () {
                clean('onload');
            };
            img.onerror = function () {
                clean('onerror');
            };
            img.onabort = function () {
                clean('onabort');
            };
            this.log(url.length, "Track data length");
            img.src = url;
        },
        _post: function (url, data, callback) {
            if (!this._form) {
                var doc = document, div = doc.createElement("div");
                div.innerHTML = '<form id="_ak_form" method="post" target="_ak_iframe"><input type="hidden" name="d"/></form><iframe id="_ak_iframe" src="about:blank"></iframe>';
                doc.body.appendChild(div);
                div.style.height = '0px';
                div.style.width = '0px';
                div.style.overflow = 'hidden';
                this._form = doc.getElementById('_ak_form');
                this._iframe = doc.getElementById('_ak_iframe');
            }
            var self = this;
            this._iframe.onload = function () {
                if (self._iframe.src != 'about:blank') {
                    self._iframe.src = 'about:blank';
                    callback && callback(data);
                }
            };
            this._form.action = url;
            this._form.d.value = data;
            this._form.submit();
        },
        _onClick: function (e) {
            this._lastActive = this.getTime();
            e = e || window.event;
            var el = e.srcElement ? e.srcElement : e.target;
            var name,
                now = this.getTime(),
                action = {t: this._ACTIONS.CLICK};
            el.id && (action.tid = el.id);
            while (_has(typeof el) && el != null) {
                name = (el.tagName || el.nodeName || '').toUpperCase();
                if (name == 'A') {
                    break;
                }
                el = el.parentNode;
            }
            if (el) {
                action.url = el.href;
                action.t = this.isInDomain(action.url) ? this._ACTIONS.INNNER_CLICK : this._ACTIONS.OUTER_CLICK;
            }
            var width = Math.max(document.documentElement.scrollWidth, document.body.offsetWidth, document.body.clientWidth),
                height = Math.max(document.documentElement.scrollHeight, document.body.offsetHeight, document.body.clientHeight),
                left, top;
            if (this._BROWSER.msie) {
                left = Math.max(document.documentElement.scrollLeft, document.body.scrollLeft);
                top = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
                left = e.clientX + left;
                top = e.clientY + top;
            } else {
                left = e.pageX;
                top = e.pageY;
            }
            if (this._lastCAdd && (now - this._lastCAdd < 1e3 || Math.abs(this._lastCLeft - left) < 15 && Math.abs(this._lastCTop - top) < 15)) {
                return;
            }
            if (left > 0 && top > 0 && left <= width && top <= height) {
                action.pos = left + ',' + top + ',' + width;
            } else {
                return;
            }
            this.trackAction(action);
            this._updateEventId();
            this._lastCLeft = left;
            this._lastCTop = top;
            this._lastCAdd = this.getTime();
        },
        _onBlur: function (e) {
            e = e || window.event;
            var el = e.srcElement ? e.srcElement : e.target,
                value = el.value,
                action = {t: this._ACTIONS.INPUT};
            if (!value || value == this._lastBValue) {
                return;
            }
            action.v = value.length > 100 ? value.substring(0, 100) : value;
            action.tid = el.id ? el.id : el.name;
            this.trackAction(action);
            this._lastBValue = value;
        },
        _updateIdle: function (ret) {
            var now = this.getTime(), idle = now - this._lastActive;
            if (idle > 1e4) {
                this._idle += Math.round(idle / 1e3);
                this.log('Total idle ' + this._idle + ' Seconds');
            }
            if (ret) {
                return this._idle;
            }
            idle > 5e3 && (this._lastActive = now);
            this._lowSpeed && this.startHeartbeat();
        },
        _updateEventId: function () {
            if (this.getCookie('ak_e') != this._eventId) {
                this.setDomainCookie('ak_e', this._eventId, null, '/');
            }
        },
        startHeartbeat: function (lowSpeed) {
            var self = this;
            this._hbt && this.stopHeartbeat();
            this._hbt = setInterval(function () {
                self.log("Do heartbeat");
                self.send(self._EVENTS.HEARTBEAT);
                if (self.getTime() - self._lastActive > 3e5) {
                    self.log("Idle 5 minutes,use low speed heartbeat");
                    //5分钟没动作,则不再发送心跳
                    self.startHeartbeat(true);
                }
            }, lowSpeed ? 3e5 : 3e4);
            this._lowSpeed = lowSpeed;
            self.log('Start ' + (lowSpeed ? 'low' : 'normal') + ' speed heartbeat');
        },
        stopHeartbeat: function () {
            clearInterval(this._hbt);
            this._hbt = null;
        },
        init: function () {
            this._cmds = _atk || [];
            var self = this;
            _domReady(function () {
                self._init();
            });
            return this;
        },
        push: function (args) {
            if (arguments.length > 1) {
                args = arguments;
            }
            if (args && args.length) {
                var cmd = args[0];
                var arg1 = args[1];
                switch (cmd) {
                    case 'start':
                        this._start = arg1;
                        break;
                    case 'slt':
                        this._slt = arg1;
                        break;
                    case 'cfs':
                        _apply(this._cfs, arg1, null, true);
                        this.log(this._cfs, "Use app configs");
                        break;
                    case 'tags':
                        this._tags = Array.prototype.slice.call(args, 1);
                        break;
                    case 'customs':
                        this.trackCustoms(arg1);
                        break;
                    case 'action':
                        for (var i = 1; i < args.length; i++) {
                            var action = args[i];
                            action.t == undefined && (action.t = this._ACTIONS.CUSTOM);
                            this.trackAction(action);
                        }
                        break;
                    case 'actionCallback':
                        this._actionCallbacks = this._actionCallbacks || [];
                        this._actionCallbacks.push(arg1);
                        break;
                    case 'ajax':
                        this.trackAjax(arg1);
                        break;
                    case 'ex':
                        this.trackEx(arg1);
                        break;
                    case 'send':
                        this.send(this._EVENTS.HEARTBEAT);
                        break;
                    case 'post':
                        this.send(this._EVENTS.HEARTBEAT, true);
                        break;
                }
            }
        },
        _hookJquery: function () {
            var self = this;
            jQuery.ajaxSetup({ global: true });
            jQuery(document)
                .ajaxSend(function (e, xhr, o) {
                    o._ts = self.getTime();
                })
                .ajaxSuccess(function (e, xhr, o) {
                    self.trackAjax({url: o.url, ok: true, lt: self.getTime() - o._ts});
                })
                .ajaxError(function (e, xhr, o) {
                    self.trackAjax({url: o.url, ok: false, lt: self.getTime() - o._ts});
                });
        },
        _init: function () {
            var self = this;
            _each(this._cmds, function () {
                self.push(this);
            });
            delete this._cmds;
            window.onerror = function (msg, url, number) {
                self.trackEx(msg + '@' + url + ':' + number);
            };
            this.bindEvent(window, 'beforeunload', function () {
                self._updateEventId();
                self.send(self._EVENTS.END);
            });
            this.bindEvent(document, 'mousemove', function (e) {
                self._updateIdle();
            });
            if (!_isFalse(this._cfs.trackClick)) {
                this.bindEvent(document, 'mousedown', function (e) {
                    self._onClick(e);
                });
            }
            if (!_isFalse(this._cfs.trackInput)) {
                var _onBlur = function (e) {
                    self._onBlur(e);
                };
                _each(document.getElementsByTagName('input'), function () {
                    self.bindEvent(this, 'blur', _onBlur);
                });
                _each(document.getElementsByTagName('textarea'), function () {
                    self.bindEvent(this, 'blur', _onBlur);
                });
            }
            if (!_isFalse(this._cfs.trackAjax) && _has(typeof jQuery)) {
                this._hookJquery();
            }
            this._idle = 0;
            this._lastActive = this.getTime();
            this.send(this._EVENTS.BEGIN);
            this.startHeartbeat();
        }
    };
})();