/*! Cola UI - 0.9.7
 * Copyright (c) 2002-2016 BSTEK Corp. All rights reserved.
 *
 * This file is dual-licensed under the AGPLv3 (http://www.gnu.org/licenses/agpl-3.0.html)
 * and BSDN commercial (http://www.bsdn.org/licenses) licenses.
 *
 * If you are unsure which license is appropriate for your use, please contact the sales department
 * at http://www.bstek.com/contact.
 */
(function() {
  var ACTIVE_PINCH_REG, ACTIVE_ROTATE_REG, ALIAS_REGEXP, EntityIndex, IGNORE_NODES, LinkedList, ON_NODE_REMOVED_KEY, PAN_VERTICAL_events, Page, SWIPE_VERTICAL_events, TYPE_SEVERITY, USER_DATA_KEY, WIDGET_TAGS_REGISTRY, _$, _DOMNodeInsertedListener, _DOMNodeRemovedListener, _Entity, _EntityList, _ExpressionDataModel, _ExpressionScope, _SYS_PARAMS, _compileResourceUrl, _compileWidgetAttribute, _compileWidgetDom, _cssCache, _destroyDomBinding, _destroyRenderableElement, _doRenderDomTemplate, _evalDataPath, _extendWidget, _filterCollection, _filterEntity, _findRouter, _findWidgetConfig, _getData, _getEntityPath, _getHashPath, _getNodeDataId, _jsCache, _loadCss, _loadHtml, _loadJs, _matchValue, _nodesToBeRemove, _numberWords, _onHashChange, _onStateChange, _setValue, _sortCollection, _switchRouter, _toJSON, _triggerWatcher, _unloadCss, _unwatch, _watch, appendChild, browser, buildContent, cola, colaEventRegistry, createContentPart, createNodeForAppend, currentRoutePath, currentRouter, defaultActionTimestamp, defaultDataTypes, definedSetting, digestExpression, doMergeDefinitions, doms, exceptionStack, getDefinition, hasDefinition, ignoreRouterSettingChange, key, oldIE, originalAjax, os, resourceStore, routerRegistry, setAttrs, setting, splitExpression, sprintf, tagSplitter, trimPath, typeRegistry, uniqueIdSeed, value, xCreate,
    slice = [].slice,
    extend = function(child, parent) { for (var key in parent) { if (hasProp.call(parent, key)) child[key] = parent[key]; } function ctor() { this.constructor = child; } ctor.prototype = parent.prototype; child.prototype = new ctor(); child.__super__ = parent.prototype; return child; },
    hasProp = {}.hasOwnProperty;

  this.cola = cola = function() {
    var ref;
    return (ref = cola["_rootFunc"]) != null ? ref.apply(cola, arguments) : void 0;
  };

  cola.util = {};

  cola.constants = {
    VARIABLE_NAME_REGEXP: /^[_a-zA-Z][_a-zA-Z0-9]*$/g,
    VIEW_CLASS: "c-view",
    VIEW_PORT_CLASS: "c-viewport",
    IGNORE_DIRECTIVE: "c-ignore",
    COLLECTION_CURRENT_CLASS: "current",
    DEFAULT_PATH: "$root",
    REPEAT_INDEX: "$index",
    DOM_USER_DATA_KEY: "_d",
    DOM_BINDING_KEY: "_binding",
    DOM_INITIALIZER_KEY: "_initialize",
    REPEAT_TEMPLATE_KEY: "_template",
    REPEAT_TAIL_KEY: "_tail",
    DOM_ELEMENT_KEY: "_element",
    DOM_SKIP_CHILDREN: "_skipChildren",
    NOT_WHITE_REG: /\S+/g,
    CLASS_REG: /[\t\r\n\f]/g,
    WIDGET_DIMENSION_UNIT: "px",
    MESSAGE_REFRESH: 0,
    MESSAGE_PROPERTY_CHANGE: 1,
    MESSAGE_CURRENT_CHANGE: 10,
    MESSAGE_EDITING_STATE_CHANGE: 11,
    MESSAGE_VALIDATION_STATE_CHANGE: 15,
    MESSAGE_INSERT: 20,
    MESSAGE_REMOVE: 21,
    MESSAGE_LOADING_START: 30,
    MESSAGE_LOADING_END: 31
  };

  oldIE = !-[1,];

  $.xCreate = xCreate = function(template, context) {
    var $el, child, content, el, element, elements, isSimpleValue, l, len1, len2, len3, o, part, q, ref, tagName, templateProcessor;
    isSimpleValue = function(value) {
      var type;
      if (value === null || value === void 0) {
        return true;
      }
      type = typeof value;
      return type !== "object" && type !== "function" || value instanceof Date;
    };
    if (template instanceof Array) {
      elements = [];
      for (l = 0, len1 = template.length; l < len1; l++) {
        part = template[l];
        element = xCreate(part, context);
        if (element != null) {
          elements.push(element);
        }
      }
      return elements;
    }
    if (xCreate.templateProcessors.length) {
      ref = xCreate.templateProcessors;
      for (o = 0, len2 = ref.length; o < len2; o++) {
        templateProcessor = ref[o];
        element = templateProcessor(template);
        if (element != null) {
          return element;
        }
      }
    }
    if (typeof template === "string") {
      if (template.charAt(0) === '^') {
        template = {
          tagName: template.substring(1)
        };
      } else {
        return document.createTextNode(template);
      }
    }
    tagName = template.tagName || "DIV";
    tagName = tagName.toUpperCase();
    if (oldIE && tagName.toUpperCase() === "INPUT" && template.type) {
      el = document.createElement("<" + tagName + " type=\"" + template.type + "\"/>");
    } else {
      el = document.createElement(tagName);
    }
    $el = $(el);
    setAttrs(el, $el, template, context);
    content = template.content;
    if (content != null) {
      if (isSimpleValue(content)) {
        if (typeof content === "string" && content.charAt(0) === '^') {
          appendChild(el, document.createElement(content.substring(1)));
        } else {
          $el.text(content);
        }
      } else {
        if (content instanceof Array) {
          for (q = 0, len3 = content.length; q < len3; q++) {
            part = content[q];
            if (isSimpleValue(part)) {
              if (typeof part === "string" && part.charAt(0) === '^') {
                appendChild(el, document.createElement(part.substring(1)));
              } else {
                appendChild(el, document.createTextNode(part));
              }
            } else {
              child = xCreate(part, context);
              if (child != null) {
                appendChild(el, child);
              }
            }
          }
        } else if (content.nodeType) {
          appendChild(el, content);
        } else {
          child = xCreate(content, context);
          if (child != null) {
            appendChild(el, child);
          }
        }
      }
    } else if (template.html) {
      $el.html(template.html);
    }
    return el;
  };

  xCreate.templateProcessors = [];

  xCreate.attributeProcessor = {
    data: function($el, attrName, attrValue, context) {
      $el.data(attrValue);
    },
    style: function($el, attrName, attrValue, context) {
      if (typeof attrValue === "string") {
        $el.attr("style", attrValue);
      } else if (attrValue !== null) {
        $el.css(attrValue);
      }
    }
  };

  setAttrs = function(el, $el, attrs, context) {
    var attrName, attrValue, attributeProcessor;
    for (attrName in attrs) {
      attrValue = attrs[attrName];
      attributeProcessor = xCreate.attributeProcessor[attrName];
      if (attributeProcessor) {
        attributeProcessor($el, attrName, attrValue, context);
      } else {
        switch (attrName) {
          case "tagName":
          case "nodeName":
          case "content":
          case "html":
            continue;
          case "contextKey":
            if (context instanceof Object && attrValue && typeof attrValue === "string") {
              context[attrValue] = el;
            }
            break;
          case "data":
            if (context instanceof Object && attrValue && typeof attrValue === "string") {
              context[attrValue] = el;
            }
            break;
          case "classname":
            $el.attr("class", attrValue);
            break;
          default:
            if (typeof attrValue === "function") {
              $el.on(attrName, attrValue);
            } else {
              $el.attr(attrName, attrValue);
            }
        }
      }
    }
  };

  appendChild = function(parentEl, el) {
    var tbody;
    if (parentEl.nodeName === "TABLE" && el.nodeName === "TR") {
      tbody;
      if (parentEl && parentEl.tBodies[0]) {
        tbody = parentEl.tBodies[0];
      } else {
        tbody = parentEl.appendChild(document.createElement("tbody"));
      }
      parentEl = tbody;
    }
    return parentEl.appendChild(el);
  };

  createNodeForAppend = function(template, context) {
    var element, fragment, l, len1, result;
    result = xCreate(template, context);
    if (!result) {
      return null;
    }
    if (result instanceof Array) {
      fragment = document.createDocumentFragment();
      for (l = 0, len1 = result.length; l < len1; l++) {
        element = result[l];
        fragment.appendChild(element);
      }
      result = fragment;
    }
    return result;
  };

  $.fn.xAppend = function(template, context) {
    var result;
    result = createNodeForAppend(template, context);
    if (!result) {
      return null;
    }
    return this.append(result);
  };

  $.fn.xInsertBefore = function(template, context) {
    var result;
    result = createNodeForAppend(template, context);
    if (!result) {
      return null;
    }
    return this.before(result);
  };

  $.fn.xInsertAfter = function(template, context) {
    var result;
    result = createNodeForAppend(template, context);
    if (!result) {
      return null;
    }
    return this.after(result);
  };

  cola.util.KeyedArray = (function() {
    KeyedArray.prototype.size = 0;

    function KeyedArray() {
      this.elements = [];
      this.keys = [];
      this.keyMap = {};
    }

    KeyedArray.prototype.add = function(key, element) {
      var i;
      if (this.keyMap.hasOwnProperty(key)) {
        i = this.elements.indexOf(element);
        if (i > -1) {
          this.elements.splice(i, 1);
          this.keys.splice(i, 1);
        }
      }
      this.keyMap[key] = element;
      this.size = this.elements.push(element);
      this.keys.push(key);
      return this;
    };

    KeyedArray.prototype.remove = function(key) {
      var element, i;
      if (typeof key === "number") {
        i = key;
        key = this.keys[i];
        element = this.elements[i];
        this.elements.splice(i, 1);
        this.keys.splice(i, 1);
        this.size = this.elements.length;
        delete this.keyMap[key];
      } else {
        element = this.keyMap[key];
        delete this.keyMap[key];
        if (element) {
          i = this.keys.indexOf(key);
          if (i > -1) {
            this.elements.splice(i, 1);
            this.keys.splice(i, 1);
            this.size = this.elements.length;
          }
        }
      }
      return element;
    };

    KeyedArray.prototype.get = function(key) {
      if (typeof key === "number") {
        return this.elements[key];
      } else {
        return this.keyMap[key];
      }
    };

    KeyedArray.prototype.getIndex = function(key) {
      if (this.keyMap.hasOwnProperty(key)) {
        return this.keys.indexOf(key);
      }
      return -1;
    };

    KeyedArray.prototype.clear = function() {
      this.elements = [];
      this.keys = [];
      this.keyMap = {};
      this.size = 0;
    };

    KeyedArray.prototype.elements = function() {
      return this.elements;
    };

    KeyedArray.prototype.each = function(fn) {
      var element, i, keys, l, len1, ref;
      keys = this.keys;
      ref = this.elements;
      for (i = l = 0, len1 = ref.length; l < len1; i = ++l) {
        element = ref[i];
        if (fn.call(this, element, keys[i]) === false) {
          break;
        }
      }
    };

    return KeyedArray;

  })();

  cola.util.trim = function(text) {
    if (text != null) {
      return String.prototype.trim.call(text);
    } else {
      return "";
    }
  };

  cola.util.capitalize = function(text) {
    if (!text) {
      return text;
    }
    return text.charAt(0).toUpperCase() + text.slice(1);
  };

  cola.util.isSimpleValue = function(value) {
    var type;
    if (value === null || value === void 0) {
      return true;
    }
    type = typeof value;
    return type !== "object" && type !== "function" || value instanceof Date || value instanceof Array;
  };

  cola.util.each = function(array, fn) {
    var i, item, l, len1;
    for (i = l = 0, len1 = array.length; l < len1; i = ++l) {
      item = array[i];
      if (fn(item, i) === false) {
        break;
      }
    }
  };

  cola.util.concatUrl = function() {
    var changed, i, l, last, len1, part, parts;
    parts = 1 <= arguments.length ? slice.call(arguments, 0) : [];
    last = parts.length - 1;
    for (i = l = 0, len1 = parts.length; l < len1; i = ++l) {
      part = parts[i];
      changed = false;
      if (i > 0 && part.charCodeAt(0) === 47) {
        part = part.substring(1);
        changed = true;
      }
      if (i < last && part.charCodeAt(part.length - 1) === 47) {
        part = part.substring(0, part.length - 1);
        changed = true;
      }
      if (changed) {
        parts[i] = part;
      }
    }
    return parts.join("/");
  };

  cola.util.parseStyleLikeString = function(styleStr, headerProp) {
    var i, j, l, len1, part, parts, style, styleExpr, styleProp;
    if (!styleStr) {
      return false;
    }
    style = {};
    parts = styleStr.split(";");
    for (i = l = 0, len1 = parts.length; l < len1; i = ++l) {
      part = parts[i];
      j = part.indexOf(":");
      if (j > 0) {
        styleProp = this.trim(part.substring(0, j));
        styleExpr = this.trim(part.substring(j + 1));
        if (styleProp && styleExpr) {
          style[styleProp] = styleExpr;
        }
      } else {
        part = this.trim(part);
        if (!part) {
          continue;
        }
        if (i === 0 && headerProp) {
          style[headerProp] = part;
        } else {
          style[part] = true;
        }
      }
    }
    return style;
  };

  cola.util.parseFunctionArgs = function(func) {
    var arg, argStr, args, i, l, len1, rawArgs;
    argStr = func.toString().match(/\([^\(\)]*\)/)[0];
    rawArgs = argStr.substring(1, argStr.length - 1).split(",");
    args = [];
    for (i = l = 0, len1 = rawArgs.length; l < len1; i = ++l) {
      arg = rawArgs[i];
      arg = cola.util.trim(arg);
      if (arg) {
        args.push(arg);
      }
    }
    return args;
  };

  cola.util.parseListener = function(listener) {
    var argStr, args, argsMode;
    argsMode = 1;
    argStr = listener.toString().match(/\([^\(\)]*\)/)[0];
    args = argStr.substring(1, argStr.length - 1).split(",");
    if (args.length) {
      if (cola.util.trim(args[0]) === "arg") {
        argsMode = 2;
      }
    }
    listener._argsMode = argsMode;
    return argsMode;
  };

  cola.util.isCompatibleType = function(baseType, type) {
    if (type === baseType) {
      return true;
    }
    while (type.__super__) {
      type = type.__super__.constructor;
      if (type === baseType) {
        return true;
      }
    }
    return false;
  };

  cola.util.delay = function(owner, name, delay, fn) {
    cola.util.cancelDelay(owner, name);
    owner["_timer_" + name] = setTimeout(function() {
      fn.call(owner);
    }, delay);
  };

  cola.util.cancelDelay = function(owner, name) {
    var key, timerId;
    key = "_timer_" + name;
    timerId = owner[key];
    if (timerId) {
      delete owner[key];
      clearTimeout(timerId);
    }
  };

  cola.util.waitForAll = function(funcs, callback) {
    var completed, func, id, l, len1, procedures, subCallback, total;
    if (!funcs || !funcs.length) {
      cola.callback(callback, true);
    }
    completed = 0;
    total = funcs.length;
    procedures = {};
    for (l = 0, len1 = funcs.length; l < len1; l++) {
      func = funcs[l];
      id = cola.uniqueId();
      procedures[id] = true;
      subCallback = {
        id: id,
        complete: function(success) {
          var disabled;
          if (disabled) {
            return;
          }
          if (success) {
            if (procedures[this.id]) {
              delete procedures[this.id];
              completed++;
              if (completed === total) {
                cola.callback(callback, true);
                disabled = true;
              }
            }
          } else {
            cola.callback(callback, false);
            disabled = true;
          }
        }
      };
      subCallback.scope = subCallback;
      func(subCallback);
    }
  };

  cola.util.formatDate = function(date, format) {
    if (date == null) {
      return "";
    }
    if (!(date instanceof XDate)) {
      date = new XDate(date);
    }
    return date.toString(format || cola.setting("defaultDateFormat"));
  };

  cola.util.formatNumber = function(number, format) {
    if (number == null) {
      return "";
    }
    if (isNaN(number)) {
      return number;
    }
    return formatNumber(format || cola.setting("defaultNumberFormat"), number);
  };

  cola.util.format = function(value, format) {
    if (value instanceof Date) {
      return cola.util.formatDate(value, format);
    } else if (isFinite(value)) {
      return cola.util.formatNumber(value, format);
    } else if (value === null || value === void 0) {
      return "";
    } else {
      return value;
    }
  };

  cola.util.isSuperClass = function(superCls, cls) {
    var ref;
    if (!superCls) {
      return false;
    }
    while (cls) {
      if (cls.__super__ === superCls.prototype) {
        return true;
      }
      cls = (ref = cls.__super__) != null ? ref.constructor : void 0;
    }
    return false;
  };

  cola.version = "${version}";

  uniqueIdSeed = 1;

  cola.uniqueId = function() {
    return "_id" + (uniqueIdSeed++);
  };

  cola.sequenceNo = function() {
    return uniqueIdSeed++;
  };

  cola._EMPTY_FUNC = function() {};

  if (typeof window !== "undefined" && window !== null) {
    (function() {
      var s, theshold, ua;
      cola.browser = {};
      cola.os = {};
      cola.device = {};
      ua = window.navigator.userAgent.toLowerCase();
      if ((s = ua.match(/webkit\/([\d.]+)/))) {
        cola.browser.webkit = s[1] || -1;
        if ((s = ua.match(/chrome\/([\d.]+)/))) {
          cola.browser.chrome = parseFloat(s[1]) || -1;
        } else if ((s = ua.match(/version\/([\d.]+).*safari/))) {
          cola.browser.safari = parseFloat(s[1]) || -1;
        }
        if ((s = ua.match(/qqbrowser\/([\d.]+)/))) {
          cola.browser.qqbrowser = parseFloat(s[1]) || -1;
        }
      } else if ((s = ua.match(/msie ([\d.]+)/))) {
        cola.browser.ie = parseFloat(s[1]) || -1;
      } else if ((s = ua.match(/trident/))) {
        cola.browser.ie = 11;
      } else if ((s = ua.match(/firefox\/([\d.]+)/))) {
        cola.browser.mozilla = parseFloat(s[1]) || -1;
      } else if ((s = ua.match(/opera.([\d.]+)/))) {
        cola.browser.opera = parseFloat(s[1]) || -1;
      }
      if ((s = ua.match(/(iphone|ipad).*os\s([\d_]+)/))) {
        cola.os.ios = parseFloat(s[2]) || -1;
        cola.device.pad = s[1] === "ipad";
        cola.device.phone = !cola.device.pad;
      } else {
        if ((s = ua.match(/(android)\s+([\d.]+)/))) {
          cola.os.android = parseFloat(s[1]) || -1;
        } else if ((s = ua.match(/(windows)[\D]*([\d]+)/))) {
          cola.os.windows = parseFloat(s[1]) || -1;
        }
      }
      if ((s = ua.match(/micromessenger\/([\d.]+)/))) {
        cola.browser.weixin = parseFloat(s[1]) || -1;
      }
      cola.device.mobile = !!(("ontouchstart" in window) && ua.match(/(mobile)/));
      cola.device.desktop = !cola.device.mobile;
      if (cola.device.mobile && !cola.os.ios) {
        theshold = 768;
        if (cola.browser.qqbrowser) {
          cola.device.pad = (window.screen.width / 2) >= theshold || (window.screen.height / 2) >= theshold;
        } else {
          cola.device.pad = window.screen.width >= theshold || window.screen.height >= theshold;
        }
        cola.device.phone = !cola.device.pad;
      }
    })();
  }


  /*
  Event
   */

  colaEventRegistry = {
    ready: {},
    settingChange: {},
    exception: {},
    beforeRouterSwitch: {},
    routerSwitch: {}
  };

  cola.on = function(eventName, listener) {
    var alias, aliasMap, i, listenerRegistry, listeners;
    i = eventName.indexOf(":");
    if (i > 0) {
      alias = eventName.substring(i + 1);
      eventName = eventName.substring(0, i);
    }
    listenerRegistry = colaEventRegistry[eventName];
    if (!listenerRegistry) {
      throw new cola.Exception("Unrecognized event \"" + eventName + "\".");
    }
    if (typeof listener !== "function") {
      throw new cola.Exception("Invalid event listener.");
    }
    listeners = listenerRegistry.listeners;
    aliasMap = listenerRegistry.aliasMap;
    if (listeners) {
      if (alias && (aliasMap != null ? aliasMap[alias] : void 0) > -1) {
        cola.off(eventName + ":" + alias);
      }
      listeners.push(listener);
      i = listeners.length - 1;
    } else {
      listenerRegistry.listeners = listeners = [listener];
      i = 0;
    }
    if (alias) {
      if (!aliasMap) {
        listenerRegistry.aliasMap = aliasMap = {};
      }
      aliasMap[alias] = i;
    }
    return this;
  };

  cola.off = function(eventName, listener) {
    var alias, aliasMap, i, listenerRegistry, listeners;
    i = eventName.indexOf(":");
    if (i > 0) {
      alias = eventName.substring(i + 1);
      eventName = eventName.substring(0, i);
    }
    listenerRegistry = colaEventRegistry[eventName];
    if (!listenerRegistry) {
      return this;
    }
    listeners = listenerRegistry.listeners;
    if (!listeners || listeners.length === 0) {
      return this;
    }
    i = -1;
    if (alias) {
      aliasMap = listenerRegistry.aliasMap;
      i = aliasMap != null ? aliasMap[alias] : void 0;
      if (i > -1) {
        if (aliasMap != null) {
          delete aliasMap[alias];
        }
        listeners.splice(i, 1);
      }
    } else if (listener) {
      i = listeners.indexOf(listener);
      if (i > -1) {
        listeners.splice(i, 1);
        aliasMap = listenerRegistry.aliasMap;
        if (aliasMap) {
          for (alias in aliasMap) {
            if (aliasMap[alias] === listener) {
              delete aliasMap[alias];
              break;
            }
          }
        }
      }
    } else {
      delete listenerRegistry.listeners;
      delete listenerRegistry.aliasMap;
    }
    return this;
  };

  cola.getListeners = function(eventName) {
    var listener, ref;
    listener = (ref = colaEventRegistry[eventName]) != null ? ref.listeners : void 0;
    if (listener != null ? listener.length : void 0) {
      return listener;
    } else {
      return null;
    }
  };

  cola.fire = function(eventName, self, arg) {
    var argsMode, l, len1, listener, listeners, ref, retValue;
    if (arg == null) {
      arg = {};
    }
    listeners = (ref = colaEventRegistry[eventName]) != null ? ref.listeners : void 0;
    if (listeners) {
      for (l = 0, len1 = listeners.length; l < len1; l++) {
        listener = listeners[l];
        argsMode = listener._argsMode;
        if (!listener._argsMode) {
          argsMode = cola.util.parseListener(listener);
        }
        if (argsMode === 1) {
          retValue = listener.call(this, self, arg);
        } else {
          retValue = listener.call(this, arg, self);
        }
        if (retValue === false) {
          return false;
        }
      }
    }
    return true;
  };

  cola.ready = function(listener) {
    return this.on("ready", listener);
  };


  /*
  Setting
   */

  setting = {
    defaultCharset: "utf-8",
    defaultNumberFormat: "#,##0.##",
    defaultDateFormat: "yyyy-MM-dd",
    defaultSubmitDateFormat: "yyyy-MM-dd'T'HH:mm:ss'T'"
  };

  cola.setting = function(key, value) {
    var k, v;
    if (typeof key === "string") {
      if (value !== void 0) {
        setting[key] = value;
        if (cola.getListeners("settingChange")) {
          cola.fire("settingChange", cola, {
            key: key
          });
        }
      } else {
        return setting[key];
      }
    } else if (typeof key === "object") {
      for (k in key) {
        v = key[k];
        setting[k] = v;
        if (cola.getListeners("settingChange")) {
          cola.fire("settingChange", cola, {
            key: k
          });
        }
      }
    }
    return this;
  };

  definedSetting = (typeof colaSetting !== "undefined" && colaSetting !== null) || (typeof global !== "undefined" && global !== null ? global.colaSetting : void 0);

  if (definedSetting) {
    for (key in definedSetting) {
      value = definedSetting[key];
      cola.setting(key, value);
    }
  }


  /*
  Exception
   */

  exceptionStack = [];

  cola.Exception = (function() {
    function Exception(message3, error1) {
      this.message = message3;
      this.error = error1;
      if (this.error) {
        if (typeof console !== "undefined" && console !== null) {
          if (typeof console.trace === "function") {
            console.trace(this.error);
          }
        }
      }
      exceptionStack.push(this);
      setTimeout((function(_this) {
        return function() {
          if (exceptionStack.indexOf(_this) > -1) {
            cola.Exception.processException(_this);
          }
        };
      })(this), 50);
    }

    Exception.processException = function(ex) {
      var ex2, scope;
      if (cola.Exception.ignoreAll) {
        return;
      }
      if (ex) {
        cola.Exception.removeException(ex);
      }
      if (ex instanceof cola.AbortException) {
        return;
      }
      if (cola.fire("exception", cola, {
        exception: ex
      }) === false) {
        return;
      }
      if (ex instanceof cola.RunnableException) {
        eval("var fn = function(){" + ex.script + "}");
        scope = typeof window !== "undefined" && window !== null ? window : this;
        fn.call(scope);
      } else {
        if (cola.Exception.ignoreAll) {
          return;
        }
        try {
          if (typeof document !== "undefined" && document !== null ? document.body : void 0) {
            if (ex.showException) {
              ex.showException();
            } else {
              cola.Exception.showException(ex);
            }
          } else {
            if (ex.safeShowException) {
              ex.safeShowException();
            } else {
              cola.Exception.safeShowException(ex);
            }
          }
        } catch (_error) {
          ex2 = _error;
          cola.Exception.removeException(ex2);
          if (ex2.safeShowException) {
            ex2.safeShowException();
          } else {
            cola.Exception.safeShowException(ex2);
          }
        }
      }
    };

    Exception.removeException = function(ex) {
      var i;
      i = exceptionStack.indexOf(ex);
      if (i > -1) {
        exceptionStack.splice(i, 1);
      }
    };

    Exception.safeShowException = function(ex) {
      var msg;
      if (ex instanceof cola.Exception || ex instanceof Error) {
        msg = ex.message;
      } else {
        msg = ex + "";
        if (typeof alert === "function") {
          alert(msg);
        }
      }
    };

    Exception.showException = function(ex) {
      return this.safeShowException(ex);
    };

    return Exception;

  })();

  cola.AbortException = (function(superClass) {
    extend(AbortException, superClass);

    function AbortException() {}

    return AbortException;

  })(cola.Exception);

  cola.RunnableException = (function(superClass) {
    extend(RunnableException, superClass);

    function RunnableException(script1) {
      this.script = script1;
      RunnableException.__super__.constructor.call(this, "[script]");
    }

    return RunnableException;

  })(cola.Exception);


  /*
  I18N
   */

  resourceStore = {};

  sprintf = function() {
    var i, l, len1, param, params, templ;
    templ = arguments[0], params = 2 <= arguments.length ? slice.call(arguments, 1) : [];
    for (i = l = 0, len1 = params.length; l < len1; i = ++l) {
      param = params[i];
      templ = templ.replace(new RegExp("\\{" + i + "\\}", "g"), param);
    }
    return templ;
  };

  cola.resource = function() {
    var bundle, key, params, str, templ;
    key = arguments[0], params = 2 <= arguments.length ? slice.call(arguments, 1) : [];
    if (typeof key === "string") {
      templ = resourceStore[key];
      if (templ != null) {
        if (params.length) {
          return sprintf.apply(this, [templ].concat(params));
        } else {
          return templ;
        }
      } else {
        return (params != null ? params[0] : void 0) || key;
      }
    } else {
      bundle = key;
      for (key in bundle) {
        str = bundle[key];
        resourceStore[key] = str;
      }
    }
  };

  cola.ResourceException = (function(superClass) {
    extend(ResourceException, superClass);

    function ResourceException() {
      var key, params;
      key = arguments[0], params = 2 <= arguments.length ? slice.call(arguments, 1) : [];
      ResourceException.__super__.constructor.call(this, cola.resource.apply(cola, [key].concat(slice.call(params))));
    }

    return ResourceException;

  })(cola.Exception);


  /*
  Methods
   */

  cola.callback = function(callback, success, result) {
    var scope;
    if (!callback) {
      return;
    }
    if (typeof callback === "function") {
      if (success) {
        return callback.call(this, result);
      }
    } else {
      scope = callback.scope || this;
      if (callback.delay) {
        setTimeout(function() {
          callback.complete.call(scope, success, result);
        }, callback.delay);
      } else {
        return callback.complete.call(scope, success, result);
      }
    }
  };

  cola.resource({
    "cola.date.monthNames": "January,February,March,April,May,June,July,August,September,October,November,December",
    "cola.date.monthNamesShort": "Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sept,Oct,Nov,Dec",
    "cola.date.dayNames": "Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday",
    "cola.date.dayNamesShort": "S,M,T,W,T,F,S",
    "cola.date.amDesignator": "AM",
    "cola.date.pmDesignator": "PM",
    "cola.date.time": "Time",
    "cola.validator.error.required": "Cannot be empty.",
    "cola.validator.error.length": "Length is not within the correct range.",
    "cola.validator.error.number": "Value is not within the correct range.",
    "cola.validator.error.email": "Email format is incorrect.",
    "cola.validator.error.url": "URL format is incorrect.",
    "cola.validator.error.regExp": "Format is incorrect.",
    "cola.messageBox.info.title": "Information",
    "cola.messageBox.warning.title": "Warning",
    "cola.messageBox.error.title": "Error",
    "cola.messageBox.question.title": "Confirm",
    "cola.message.approve": "Ok",
    "cola.message.deny": "Cancel",
    "cola.pager.info": "Page:{0}\/{1}"
  });

  _toJSON = function(data) {
    var p, rawData, v;
    if (data) {
      if (typeof data === "object") {
        if (data instanceof cola.Entity || data instanceof cola.EntityList) {
          data = data.toJSON();
        } else if (data instanceof Date) {
          data = cola.util.formatDate(data, cola.setting("defaultSubmitDateFormat"));
        } else {
          rawData = data;
          data = {};
          for (p in rawData) {
            v = rawData[p];
            data[p] = _toJSON(v);
          }
        }
      } else if (typeof data === "function") {
        data = void 0;
      }
    }
    return data;
  };

  originalAjax = jQuery.ajax;

  $.ajax = function(url, settings) {
    var data, p, rawData, v;
    if (typeof url === "object" && !settings) {
      settings = url;
    }
    data = settings.data;
    if (data) {
      if (typeof data === "object") {
        if (data instanceof cola.Entity || data instanceof cola.EntityList) {
          data = data.toJSON();
        } else if (!(data instanceof FormData)) {
          rawData = data;
          data = {};
          for (p in rawData) {
            v = rawData[p];
            data[p] = _toJSON(v);
          }
        } else if (data instanceof Date) {
          data = _toJSON(data);
        }
      } else if (typeof data === "function") {
        data = void 0;
      }
      settings.data = data;
    }
    return originalAjax.apply(this, [url, settings]);
  };

  tagSplitter = " ";

  doMergeDefinitions = function(definitions, mergeDefinitions, overwrite) {
    var definition, mergeDefinition, name, prop;
    if (definitions === mergeDefinitions) {
      return;
    }
    for (name in mergeDefinitions) {
      mergeDefinition = mergeDefinitions[name];
      if (definitions.$has(name)) {
        definition = definitions[name];
        if (definition && mergeDefinition) {
          for (prop in mergeDefinition) {
            if (overwrite || !definition.hasOwnProperty(prop)) {
              definition[prop] = mergeDefinition[prop];
            }
          }
        } else {
          definitions[name] = mergeDefinition;
        }
      } else {
        definitions[name] = mergeDefinition;
      }
    }
  };

  hasDefinition = function(name) {
    return this.hasOwnProperty(name.toLowerCase());
  };

  getDefinition = function(name) {
    return this[name.toLowerCase()];
  };

  cola.preprocessClass = function(classType) {
    var attributes, definition, events, name, realName, ref, superType;
    if (!(!classType.attributes._inited || !classType.events._inited)) {
      return;
    }
    superType = (ref = classType.__super__) != null ? ref.constructor : void 0;
    if (superType) {
      if (superType && (!superType.attributes._inited || !superType.events._inited)) {
        cola.preprocessClass(superType);
      }
      attributes = classType.attributes;
      if (!attributes._inited) {
        attributes._inited = true;
        for (name in attributes) {
          definition = attributes[name];
          realName = name.toLowerCase();
          if (name !== realName) {
            if (definition == null) {
              definition = {};
            }
            definition.name = name;
            attributes[realName] = definition;
            delete attributes[name];
          }
        }
        attributes.$has = hasDefinition;
        attributes.$get = getDefinition;
        doMergeDefinitions(attributes, superType.attributes, false);
      }
      events = classType.events;
      if (!events._inited) {
        events._inited = true;
        for (name in events) {
          definition = events[name];
          realName = name.toLowerCase();
          if (name !== realName) {
            events[realName] = definition;
            delete events[name];
          }
        }
        events.$has = hasDefinition;
        events.$get = getDefinition;
        doMergeDefinitions(events, superType.events, false);
      }
    }
  };

  cola.Element = (function() {
    Element.mixin = function(classType, mixin) {
      var attributes, events, member, mixInEvents, mixinAttributes, name;
      for (name in mixin) {
        member = mixin[name];
        if (name === "attributes") {
          mixinAttributes = member;
          if (mixinAttributes) {
            attributes = classType.attributes != null ? classType.attributes : classType.attributes = {};
            doMergeDefinitions(attributes, mixinAttributes, true);
          }
        } else if (name === "events") {
          mixInEvents = member;
          if (mixInEvents) {
            events = classType.events != null ? classType.events : classType.events = {};
            doMergeDefinitions(events, mixInEvents, true);
          }
        } else if (name === "constructor") {
          if (!classType._constructors) {
            classType._constructors = [member];
          } else {
            classType._constructors.push(member);
          }
        } else if (name === "destroy") {
          if (!classType._destructors) {
            classType._destructors = [member];
          } else {
            classType._destructors.push(member);
          }
        } else {
          classType.prototype[name] = member;
        }
      }
    };

    Element.attributes = {
      model: {
        readOnly: true,
        getter: function() {
          return this._scope;
        }
      },
      tag: {
        getter: function() {
          if (this._tag) {
            return this._tag.join(tagSplitter);
          } else {
            return null;
          }
        },
        setter: function(tag) {
          var l, len1, len2, o, ref, t, ts;
          if (this._tag) {
            ref = this._tag;
            for (l = 0, len1 = ref.length; l < len1; l++) {
              t = ref[l];
              cola.tagManager.unreg(t, this);
            }
          }
          if (tag) {
            this._tag = ts = tag.split(tagSplitter);
            for (o = 0, len2 = ts.length; o < len2; o++) {
              t = ts[o];
              cola.tagManager.reg(t, this);
            }
          } else {
            this._tag = null;
          }
        }
      },
      userData: null
    };

    Element.events = {
      create: null,
      attributeChange: null,
      destroy: null
    };

    function Element(config) {
      var attr, attrConfig, attrConfigs, classType, constructor, l, len1, ref;
      classType = this.constructor;
      if (!classType.attributes._inited || !classType.events._inited) {
        cola.preprocessClass(classType);
      }
      this._constructing = true;
      this._scope = (config != null ? config.scope : void 0) || cola.currentScope;
      attrConfigs = classType.attributes;
      for (attr in attrConfigs) {
        attrConfig = attrConfigs[attr];
        if ((attrConfig != null ? attrConfig.defaultValue : void 0) !== void 0) {
          if (attrConfig.setter) {
            attrConfig.setter.call(this, attrConfig.defaultValue, attr);
          } else {
            this["_" + ((attrConfig != null ? attrConfig.name : void 0) || attr)] = attrConfig.defaultValue;
          }
        }
      }
      if (classType._constructors) {
        ref = classType._constructors;
        for (l = 0, len1 = ref.length; l < len1; l++) {
          constructor = ref[l];
          constructor.call(this);
        }
      }
      if (config) {
        this.set(config, true);
      }
      delete this._constructing;
      this.fire("create", this);
    }

    Element.prototype.destroy = function() {
      var classType, destructor, elementAttrBinding, l, len1, p, ref, ref1;
      classType = this.constructor;
      if (classType._destructors) {
        ref = classType._destructors;
        for (l = 0, len1 = ref.length; l < len1; l++) {
          destructor = ref[l];
          destructor.call(this);
        }
      }
      if (this._elementAttrBindings) {
        ref1 = this._elementAttrBindings;
        for (p in ref1) {
          elementAttrBinding = ref1[p];
          elementAttrBinding.destroy();
        }
      }
      this.fire("destroy", this);
      if (this._tag) {
        this._set("tag", null);
      }
    };

    Element.prototype.get = function(attr, ignoreError) {
      var l, len1, obj, path, paths;
      if (attr.indexOf(".") > -1) {
        paths = attr.split(".");
        obj = this;
        for (l = 0, len1 = paths.length; l < len1; l++) {
          path = paths[l];
          if (obj instanceof cola.Element) {
            obj = obj._get(path, ignoreError);
          } else if (typeof obj.get === "function") {
            obj = obj.get(path);
          } else {
            obj = obj[path];
          }
          if (obj == null) {
            break;
          }
        }
        return obj;
      } else {
        return this._get(attr, ignoreError);
      }
    };

    Element.prototype._get = function(attr, ignoreError) {
      var attrConfig;
      if (!this.constructor.attributes.$has(attr)) {
        if (ignoreError) {
          return;
        }
        throw new cola.Exception("Unrecognized Attribute \"" + attr + "\".");
      }
      attrConfig = this.constructor.attributes[attr.toLowerCase()];
      if (attrConfig != null ? attrConfig.getter : void 0) {
        return attrConfig.getter.call(this, attr);
      } else {
        return this["_" + attr];
      }
    };

    Element.prototype.set = function(attr, value, ignoreError) {
      var config, i, l, len1, obj, path, paths;
      if (typeof attr === "string") {
        if (attr.indexOf(".") > -1) {
          paths = attr.split(".");
          obj = this;
          for (i = l = 0, len1 = paths.length; l < len1; i = ++l) {
            path = paths[i];
            if (obj instanceof cola.Element) {
              obj = obj._get(path, ignoreError);
            } else {
              obj = obj[path];
            }
            if (obj == null) {
              break;
            }
            if (i >= (paths.length - 2)) {
              break;
            }
          }
          if ((obj == null) && !ignoreError) {
            throw new cola.Exception("Cannot set attribute \"" + (path.slice(0, i).join(".")) + "\" of undefined.");
          }
          if (obj instanceof cola.Element) {
            obj._set(paths[paths.length - 1], value, ignoreError);
          } else if (typeof obj.set === "function") {
            obj.set(paths[paths.length - 1], value);
          } else {
            obj[paths[paths.length - 1]] = value;
          }
        } else {
          this._set(attr, value, ignoreError);
        }
      } else {
        config = attr;
        ignoreError = value;
        for (attr in config) {
          this.set(attr, config[attr], ignoreError);
        }
      }
      return this;
    };

    Element.prototype._set = function(attr, value, ignoreError) {
      var action, attrConfig, eventName, expression, i, parts, ref, scope;
      if (typeof value === "string" && this._scope) {
        if (value.charCodeAt(0) === 123) {
          parts = cola._compileText(value);
          if ((parts != null ? parts.length : void 0) > 0) {
            value = parts[0];
          }
        }
      }
      if (this.constructor.attributes.$has(attr)) {
        attrConfig = this.constructor.attributes[attr.toLowerCase()];
        if (attrConfig) {
          if (attrConfig.readOnly) {
            if (ignoreError) {
              return;
            }
            throw new cola.Exception("Attribute \"" + attr + "\" is readonly.");
          }
          if (!this._constructing && attrConfig.readOnlyAfterCreate) {
            if (ignoreError) {
              return;
            }
            throw new cola.Exception("Attribute \"" + attr + "\" cannot be changed after create.");
          }
        }
      } else if (value) {
        eventName = attr;
        i = eventName.indexOf(":");
        if (i > 0) {
          eventName = eventName.substring(0, i);
        }
        if (this.constructor.events.$has(eventName)) {
          if (value instanceof cola.Expression) {
            expression = value;
            scope = this._scope;
            this.on(attr, function(self, arg) {
              expression.evaluate(scope, "never", {
                vars: {
                  $self: self,
                  $arg: arg
                }
              });
            }, ignoreError);
            return;
          } else if (typeof value === "function") {
            this.on(attr, value);
            return;
          } else if (typeof value === "string") {
            action = (ref = this._scope) != null ? ref.action(value) : void 0;
            if (action) {
              this.on(attr, action);
              return;
            }
          }
        }
        if (ignoreError) {
          return;
        }
        throw new cola.Exception("Unrecognized Attribute \"" + attr + "\".");
      }
      this._doSet(attr, attrConfig, value);
      if (this._eventRegistry) {
        if (this.getListeners("attributeChange")) {
          this.fire("attributeChange", this, {
            attribute: attr
          });
        }
      }
    };

    Element.prototype._doSet = function(attr, attrConfig, value) {
      var elementAttrBinding, elementAttrBindings, expression, scope;
      if (!this._duringBindingRefresh && this._elementAttrBindings) {
        elementAttrBinding = this._elementAttrBindings[attr];
        if (elementAttrBinding) {
          elementAttrBinding.destroy();
          delete this._elementAttrBindings[attr];
        }
      }
      if (value instanceof cola.Expression && cola.currentScope) {
        expression = value;
        scope = cola.currentScope;
        if (expression.isStatic) {
          value = expression.evaluate(scope, "never");
        } else {
          elementAttrBinding = new cola.ElementAttrBinding(this, attr, expression, scope);
          if (this._elementAttrBindings == null) {
            this._elementAttrBindings = {};
          }
          elementAttrBindings = this._elementAttrBindings;
          if (elementAttrBindings) {
            elementAttrBindings[attr] = elementAttrBinding;
          }
          value = elementAttrBinding.evaluate();
        }
      }
      if (attrConfig) {
        if (attrConfig.type === "boolean") {
          if ((value != null) && typeof value !== "boolean") {
            value = value === "true";
          }
        } else if (attrConfig.type === "number") {
          if ((value != null) && typeof value !== "number") {
            value = parseFloat(value) || 0;
          }
        }
        if (attrConfig["enum"] && attrConfig["enum"].indexOf(value) < 0) {
          throw new cola.Exception("The value \"" + value + "\" of attribute \"" + attr + "\" is out of range.");
        }
        if (attrConfig.setter) {
          attrConfig.setter.call(this, value, attr);
          return;
        }
      }
      this["_" + ((attrConfig != null ? attrConfig.name : void 0) || attr)] = value;
    };

    Element.prototype._on = function(eventName, listener, alias, once) {
      var aliasMap, eventConfig, i, listenerRegistry, listeners;
      eventName = eventName.toLowerCase();
      eventConfig = this.constructor.events[eventName];
      if (this._eventRegistry) {
        listenerRegistry = this._eventRegistry[eventName];
      } else {
        this._eventRegistry = {};
      }
      if (!listenerRegistry) {
        this._eventRegistry[eventName] = listenerRegistry = {};
      }
      if (once) {
        if (listenerRegistry.onceListeners == null) {
          listenerRegistry.onceListeners = [];
        }
        listenerRegistry.onceListeners.push(listener);
      }
      listeners = listenerRegistry.listeners;
      aliasMap = listenerRegistry.aliasMap;
      if (listeners) {
        if ((eventConfig != null ? eventConfig.singleListener : void 0) && listeners.length) {
          throw new cola.Exception("Multi listeners is not allowed for event \"" + eventName + "\".");
        }
        if (alias && (aliasMap != null ? aliasMap[alias] : void 0) > -1) {
          cola.off(eventName + ":" + alias);
        }
        listeners.push(listener);
        i = listeners.length - 1;
      } else {
        listenerRegistry.listeners = listeners = [listener];
        i = 0;
      }
      if (alias) {
        if (!aliasMap) {
          listenerRegistry.aliasMap = aliasMap = {};
        }
        aliasMap[alias] = i;
      }
    };

    Element.prototype.on = function(eventName, listener, once) {
      var alias, i;
      i = eventName.indexOf(":");
      if (i > 0) {
        alias = eventName.substring(i + 1);
        eventName = eventName.substring(0, i);
      }
      if (!this.constructor.events.$has(eventName)) {
        throw new cola.Exception("Unrecognized event \"" + eventName + "\".");
      }
      if (typeof listener !== "function") {
        throw new cola.Exception("Invalid event listener.");
      }
      this._on(eventName, listener, alias, once);
      return this;
    };

    Element.prototype.one = function(eventName, listener) {
      return this.on(eventName, listener, true);
    };

    Element.prototype._off = function(eventName, listener, alias) {
      var aliasMap, i, listenerRegistry, listeners, onceListeners;
      eventName = eventName.toLowerCase();
      listenerRegistry = this._eventRegistry[eventName];
      if (!listenerRegistry) {
        return this;
      }
      listeners = listenerRegistry.listeners;
      if (!(listeners && listeners.length)) {
        return this;
      }
      i = -1;
      if (alias || listener) {
        if (alias) {
          aliasMap = listenerRegistry.aliasMap;
          i = aliasMap != null ? aliasMap[alias] : void 0;
          if (i > -1) {
            if (aliasMap != null) {
              delete aliasMap[alias];
            }
            listener = listeners[i];
            listeners.splice(i, 1);
          }
        } else if (listener) {
          i = listeners.indexOf(listener);
          if (i > -1) {
            listeners.splice(i, 1);
            aliasMap = listenerRegistry.aliasMap;
            if (aliasMap) {
              for (alias in aliasMap) {
                if (aliasMap[alias] === listener) {
                  delete aliasMap[alias];
                  break;
                }
              }
            }
          }
        }
        if (listenerRegistry.onceListeners && listener) {
          onceListeners = listenerRegistry.onceListeners;
          i = onceListeners.indexOf(listener);
          if (i > -1) {
            onceListeners.splice(i, 1);
            if (!onceListeners.length) {
              delete listenerRegistry.onceListeners;
            }
          }
        }
      } else {
        delete listenerRegistry.listeners;
        delete listenerRegistry.aliasMap;
      }
    };

    Element.prototype.off = function(eventName, listener) {
      var alias, i;
      if (!this._eventRegistry) {
        return this;
      }
      i = eventName.indexOf(":");
      if (i > 0) {
        alias = eventName.substring(i + 1);
        eventName = eventName.substring(0, i);
      }
      this._off(eventName, listener, alias);
      return this;
    };

    Element.prototype.getListeners = function(eventName) {
      var ref, ref1;
      return (ref = this._eventRegistry) != null ? (ref1 = ref[eventName.toLowerCase()]) != null ? ref1.listeners : void 0 : void 0;
    };

    Element.prototype.fire = function(eventName, self, arg) {
      var argsMode, l, len1, len2, listener, listenerRegistry, listeners, o, oldScope, onceListeners, result, retValue;
      if (!this._eventRegistry) {
        return;
      }
      eventName = eventName.toLowerCase();
      result = void 0;
      listenerRegistry = this._eventRegistry[eventName];
      if (listenerRegistry) {
        listeners = listenerRegistry.listeners;
        if (listeners) {
          if (arg) {
            arg.model = this._scope;
          } else {
            arg = {
              model: this._scope
            };
          }
          oldScope = cola.currentScope;
          cola.currentScope = this._scope;
          try {
            for (l = 0, len1 = listeners.length; l < len1; l++) {
              listener = listeners[l];
              if (typeof listener === "function") {
                argsMode = listener._argsMode;
                if (!listener._argsMode) {
                  argsMode = cola.util.parseListener(listener);
                }
                if (argsMode === 1) {
                  retValue = listener.call(self, self, arg);
                } else {
                  retValue = listener.call(self, arg, self);
                }
              } else if (typeof listener === "string") {
                retValue = (function(_this) {
                  return function(self, arg) {
                    return eval(listener);
                  };
                })(this)(self, arg);
              }
              if (retValue !== void 0) {
                result = retValue;
              }
              if (retValue === false) {
                break;
              }
            }
          } finally {
            cola.currentScope = oldScope;
          }
          if (listenerRegistry.onceListeners) {
            onceListeners = listenerRegistry.onceListeners.slice();
            delete listenerRegistry.onceListeners;
            for (o = 0, len2 = onceListeners.length; o < len2; o++) {
              listener = onceListeners[o];
              this.off(eventName, listener);
            }
          }
        }
      }
      return result;
    };

    return Element;

  })();

  cola.Definition = (function(superClass) {
    extend(Definition, superClass);

    Definition.attributes = {
      name: {
        readOnlyAfterCreate: true
      }
    };

    function Definition(config) {
      var scope;
      if (config != null ? config.name : void 0) {
        this._name = config.name;
        delete config.name;
        scope = (config != null ? config.scope : void 0) || cola.currentScope;
        if (scope) {
          scope.data.regDefinition(this);
        }
      }
      Definition.__super__.constructor.call(this, config);
    }

    return Definition;

  })(cola.Element);


  /*
      Element Group
   */

  cola.Element.createGroup = function(elements, model) {
    var ele, l, len1, scope;
    if (model) {
      elements = [];
      for (l = 0, len1 = elements.length; l < len1; l++) {
        ele = elements[l];
        if (ele._scope && !ele._model) {
          scope = ele._scope;
          while (scope) {
            if (scope instanceof cola.Scope) {
              ele._model = scope;
              break;
            }
            scope = scope.parent;
          }
        }
        if (ele._model === model) {
          elements.push(ele);
        }
      }
    } else {
      elements = elements ? elements.slice(0) : [];
    }
    elements.set = function(attr, value) {
      var element, len2, o;
      for (o = 0, len2 = elements.length; o < len2; o++) {
        element = elements[o];
        element.set(attr, value);
      }
      return this;
    };
    elements.on = function(eventName, listener, once) {
      var element, len2, o;
      for (o = 0, len2 = elements.length; o < len2; o++) {
        element = elements[o];
        element.on(eventName, listener, once);
      }
      return this;
    };
    elements.off = function(eventName) {
      var element, len2, o;
      for (o = 0, len2 = elements.length; o < len2; o++) {
        element = elements[o];
        element.off(eventName);
      }
      return this;
    };
    return elements;
  };


  /*
      Tag Manager
   */

  cola.tagManager = {
    registry: {},
    reg: function(tag, element) {
      var elements;
      elements = this.registry[tag];
      if (elements) {
        elements.push(element);
      } else {
        this.registry[tag] = [element];
      }
    },
    unreg: function(tag, element) {
      var elements, i;
      if (element) {
        elements = this.registry[tag];
        if (elements) {
          i = elements.indexOf(element);
          if (i > -1) {
            if (i === 0 && elements.length === 1) {
              delete this.registry[tag];
            } else {
              elements.splice(i, 1);
            }
          }
        }
      } else {
        delete this.registry[tag];
      }
    },
    find: function(tag) {
      return this.registry[tag];
    }
  };

  cola.tag = function(tag) {
    var elements;
    elements = cola.tagManager.find(tag);
    return cola.Element.createGroup(elements);
  };


  /*
      Type Registry
   */

  typeRegistry = {};

  cola.registerType = function(namespace, typeName, constructor) {
    var holder;
    holder = typeRegistry[namespace] || (typeRegistry[namespace] = {});
    holder[typeName] = constructor;
  };

  cola.registerTypeResolver = function(namespace, typeResolver) {
    var holder;
    holder = typeRegistry[namespace] || (typeRegistry[namespace] = {});
    if (holder._resolvers == null) {
      holder._resolvers = [];
    }
    holder._resolvers.push(typeResolver);
  };

  cola.resolveType = function(namespace, config, baseType) {
    var constructor, holder, l, len1, ref, resolver;
    constructor = null;
    holder = typeRegistry[namespace];
    if (holder) {
      constructor = holder[(config != null ? config.$type : void 0) || "_default"];
      if (!constructor && holder._resolvers) {
        ref = holder._resolvers;
        for (l = 0, len1 = ref.length; l < len1; l++) {
          resolver = ref[l];
          constructor = resolver(config);
          if (constructor) {
            if (baseType && !cola.util.isCompatibleType(baseType, constructor)) {
              throw new cola.Exception("Incompatiable class type.");
            }
            break;
          }
        }
      }
      return constructor;
    }
  };

  cola.create = function(namespace, config, baseType) {
    var constr;
    if (typeof config === "string") {
      config = {
        $type: config
      };
    }
    constr = cola.resolveType(namespace, config, baseType);
    return new constr(config);
  };

  if (XDate) {
    if (typeof $ === "function") {
      $(function() {
        var localeStrings;
        XDate.locales[''] = localeStrings = {};
        if (cola.resource("cola.date.monthNames")) {
          localeStrings.monthNames = cola.resource("cola.date.monthNames", 6).split(",");
        }
        if (cola.resource("cola.date.monthNamesShort")) {
          localeStrings.monthNamesShort = cola.resource("cola.date.monthNamesShort").split(",");
        }
        if (cola.resource("cola.date.dayNames")) {
          localeStrings.dayNames = cola.resource("cola.date.dayNames").split(",");
        }
        if (cola.resource("cola.date.dayNamesShort")) {
          localeStrings.dayNamesShort = cola.resource("cola.date.dayNamesShort").split(",");
        }
        if (cola.resource("cola.date.amDesignator")) {
          localeStrings.amDesignator = cola.resource("cola.date.amDesignator");
        }
        if (cola.resource("cola.date.pmDesignator")) {
          localeStrings.pmDesignator = cola.resource("cola.date.pmDesignator");
        }
      });
    }
    XDate.parsers.push(function(str) {
      var c, dateStr, digit, digits, format, hasText, i, inQuota, l, len1, len2, o, part, parts, pattern, patterns, shouldReturn, start;
      if (str.indexOf("||") < 0) {
        return;
      }
      parts = str.split("||");
      format = parts[0];
      dateStr = parts[1];
      parts = {
        y: {
          len: 0,
          value: 1900
        },
        M: {
          len: 0,
          value: 1
        },
        d: {
          len: 0,
          value: 1
        },
        h: {
          len: 0,
          value: 0
        },
        m: {
          len: 0,
          value: 0
        },
        s: {
          len: 0,
          value: 0
        }
      };
      patterns = [];
      hasText = false;
      inQuota = false;
      i = 0;
      while (i < format.length) {
        c = format.charAt(i);
        if (c === "\"") {
          hasText = true;
          if (inQuota === c) {
            inQuota = false;
          } else if (!inQuota) {
            inQuota = c;
          }
        } else if (!inQuota && parts[c]) {
          if (parts[c].len === 0) {
            patterns.push(c);
          }
          parts[c].len++;
        } else {
          hasText = true;
        }
        i++;
      }
      shouldReturn = false;
      if (!hasText) {
        if (dateStr.match(/^\d{2,14}$/)) {
          shouldReturn = true;
          start = 0;
          for (l = 0, len1 = patterns.length; l < len1; l++) {
            pattern = patterns[l];
            part = parts[pattern];
            if (part.len) {
              digit = dateStr.substring(start, start + part.len);
              part.value = parseInt(digit, 10);
              start += part.len;
            }
          }
        }
      } else {
        digits = dateStr.split(/\D+/);
        if (digits[digits.length - 1] === "") {
          digits.splice(digits.length - 1, 1);
        }
        if (digits[0] === "") {
          digits.splice(0, 1);
        }
        if (patterns.length === digits.length) {
          shouldReturn = true;
          for (i = o = 0, len2 = patterns.length; o < len2; i = ++o) {
            pattern = patterns[i];
            parts[pattern].value = parseInt(digits[i], 10);
          }
        }
      }
      if (shouldReturn) {
        return new XDate(parts.y.value, parts.M.value - 1, parts.d.value, parts.h.value, parts.m.value, parts.s.value);
      } else {

      }
    });
  }

  cola._compileText = function(text) {
    var expr, exprStr, p, parts, s;
    p = 0;
    s = 0;
    while ((s = text.indexOf("{{", p)) > -1) {
      exprStr = digestExpression(text, s + 2);
      if (exprStr) {
        if (s > p) {
          if (!parts) {
            parts = [];
          }
          parts.push(text.substring(p, s));
        }
        expr = cola._compileExpression(exprStr, exprStr.indexOf(" in ") > 0 ? "repeat" : void 0);
        if (!parts) {
          parts = [expr];
        } else {
          parts.push(expr);
        }
        p = s + exprStr.length + 4;
      } else {
        break;
      }
    }
    if (parts) {
      if (p < text.length - 1) {
        parts.push(text.substring(p));
      }
      return parts;
    } else {
      return null;
    }
  };

  digestExpression = function(text, p) {
    var c, endBracket, len, quota, s;
    s = p;
    len = text.length;
    endBracket = 0;
    while (p < len) {
      c = text.charCodeAt(p);
      if (c === 125 && !quota) {
        if (endBracket === 1) {
          return text.substring(s, p - 1);
        }
        endBracket++;
      } else {
        endBracket = 0;
        if (c === 39 || c === 34) {
          if (quota) {
            if (quota === c) {
              quota = false;
            }
          } else {
            quota = c;
          }
        }
      }
      p++;
    }
  };

  cola._compileExpression = function(exprStr, specialType) {
    var aliasName, exp, i;
    if (!exprStr) {
      return null;
    }
    if (specialType === "repeat") {
      i = exprStr.indexOf(" in ");
      if (i > 0) {
        aliasName = exprStr.substring(0, i);
        if (aliasName.match(cola.constants.VARIABLE_NAME_REGEXP)) {
          exprStr = exprStr.substring(i + 4);
          if (!exprStr) {
            return null;
          }
          exp = new cola.Expression(exprStr, true);
          exp.raw = aliasName + " in " + exp.raw;
          exp.repeat = true;
          exp.alias = aliasName;
        }
        if (!exp) {
          throw new cola.Exception("\"" + exprStr + "\" is not a valid expression.");
        }
      } else {
        exp = new cola.Expression(exprStr, true);
        exp.repeat = true;
        exp.alias = "item";
      }
    } else if (specialType === "alias") {
      i = exprStr.indexOf(" as ");
      if (i > 0) {
        aliasName = exprStr.substring(i + 4);
        if (aliasName && aliasName.match(cola.constants.VARIABLE_NAME_REGEXP)) {
          exprStr = exprStr.substring(0, i);
          if (!exprStr) {
            return null;
          }
          exp = new cola.Expression(exprStr, true);
          exp.raw = exp.raw + " as " + aliasName;
          exp.setAlias = true;
          exp.alias = aliasName;
        }
      }
      if (!exp) {
        throw new cola.Exception("\"" + exprStr + "\" should be a alias expression.");
      }
    } else {
      exp = new cola.Expression(exprStr, true);
    }
    return exp;
  };

  splitExpression = function(text, separator) {
    var c, i, len, p, part, parts, quota, separatorCharCode;
    separatorCharCode = separator.charCodeAt(0);
    parts = null;
    p = 0;
    i = 0;
    len = text.length;
    while (i < len) {
      c = text.charCodeAt(i);
      if (c === separatorCharCode && !quota) {
        part = text.substring(p, i);
        if (parts == null) {
          parts = [];
        }
        parts.push(cola.util.trim(part));
        p = i + 1;
      } else {
        if (c === 39 || c === 34) {
          if (quota) {
            if (quota === c) {
              quota = false;
            }
          } else {
            quota = c;
          }
        }
      }
      i++;
    }
    if (p < len) {
      part = text.substring(p);
      if (parts == null) {
        parts = [];
      }
      parts.push(cola.util.trim(part));
    }
    return parts;
  };

  cola.Expression = (function() {
    function Expression(exprStr) {
      var fc, i, j, l, last, len1, len2, o, oldParts, part, parts, path, ref, watchPathStr, watchPaths;
      this.raw = exprStr;
      i = exprStr.indexOf(" on ");
      if ((0 < i && i < (exprStr.length - 1))) {
        watchPathStr = exprStr.substring(i + 4);
        exprStr = exprStr.substring(0, i);
        watchPaths = [];
        ref = watchPathStr.split(/[,;]/);
        for (l = 0, len1 = ref.length; l < len1; l++) {
          path = ref[l];
          path = cola.util.trim(path);
          if (!path) {
            continue;
          }
          if (path.indexOf(".") > 0) {
            parts = [];
            oldParts = path.split(".");
            last = oldParts.length - 1;
            for (j = o = 0, len2 = oldParts.length; o < len2; j = ++o) {
              part = oldParts[j];
              if (j < last && part.charCodeAt(0) !== 33) {
                part = "!" + part;
              }
              parts.push(part);
            }
            path = parts.join(".");
          }
          watchPaths.push(path);
        }
      }
      fc = exprStr.charCodeAt(0);
      if (fc === 61) {
        exprStr = exprStr.substring(1);
        this.isStatic = true;
      } else if (fc === 63) {
        exprStr = exprStr.substring(1);
        this.isDyna = true;
      }
      this.compile(exprStr);
      if (watchPaths) {
        this.paths = watchPaths;
      }
    }

    Expression.prototype.compile = function(exprStr) {
      var parts, pathParts, stringify, tree;
      stringify = function(node, parts, pathParts, close, context) {
        var argument, callee, element, i, l, len1, len2, o, path, ref, ref1, ref2, type;
        type = node.type;
        switch (type) {
          case "MemberExpression":
          case "Identifier":
          case "ThisExpression":
            if (type === "MemberExpression") {
              stringify(node.object, parts, pathParts, false, context);
              if (pathParts.length) {
                pathParts.push(node.property.name);
              } else {
                parts.push(".");
                parts.push(node.property.name);
              }
            } else {
              pathParts.push(node.name);
            }
            break;
          case "CallExpression":
            context.hasCallStatement = true;
            callee = node.callee;
            if (callee.type === "Identifier") {
              parts.push("scope.action(\"");
              parts.push(node.callee.name);
              parts.push("\")(");
            } else if (callee.type === "MemberExpression") {
              stringify(callee.object, parts, pathParts, true, context);
              parts.push(".");
              parts.push(callee.property.name);
              parts.push("(");
            } else {
              throw new cola.Exception("\"" + exprStr + "\" invalid callee.");
            }
            if ((ref = node["arguments"]) != null ? ref.length : void 0) {
              ref1 = node["arguments"];
              for (i = l = 0, len1 = ref1.length; l < len1; i = ++l) {
                argument = ref1[i];
                if (i > 0) {
                  parts.push(",");
                }
                stringify(argument, parts, pathParts, true, context);
              }
            }
            parts.push(")");
            break;
          case "Literal":
            parts.push(node.raw);
            break;
          case "BinaryExpression":
          case "LogicalExpression":
            parts.push("(");
            stringify(node.left, parts, pathParts, true, context);
            parts.push(node.operator);
            stringify(node.right, parts, pathParts, true, context);
            parts.push(")");
            break;
          case "UnaryExpression":
            parts.push(node.operator);
            stringify(node.argument, parts, pathParts, true, context);
            break;
          case "ConditionalExpression":
            parts.push("(");
            stringify(node.test, parts, pathParts, true, context);
            parts.push("?");
            stringify(node.consequent, parts, pathParts, true, context);
            parts.push(":");
            stringify(node.alternate, parts, pathParts, true, context);
            parts.push(")");
            break;
          case "ArrayExpression":
            parts.push("[");
            ref2 = node.elements;
            for (i = o = 0, len2 = ref2.length; o < len2; i = ++o) {
              element = ref2[i];
              if (i > 0) {
                parts.push(",");
              }
              stringify(element, parts, pathParts, true, context);
            }
            parts.push("]");
        }
        if (close && pathParts.length) {
          path = pathParts.join(".");
          if (!context.paths) {
            context.paths = [path];
          } else {
            context.paths.push(path);
          }
          parts.push("_getData(scope,'");
          parts.push(path);
          parts.push("',loadMode,dataCtx)");
          pathParts.splice(0, pathParts.length);
        }
      };
      tree = jsep(exprStr);
      this.type = tree.type;
      parts = [];
      pathParts = [];
      stringify(tree, parts, pathParts, true, this);
      this.expression = parts.join("");
    };

    Expression.prototype.evaluate = function(scope, loadMode, dataCtx) {
      var retValue;
      retValue = eval(this.expression);
      if (retValue instanceof cola.Chain) {
        retValue = retValue._data;
      }
      if (retValue instanceof cola.Entity || retValue instanceof cola.EntityList) {
        if (dataCtx != null) {
          dataCtx.path = retValue.getPath();
        }
      }
      return retValue;
    };

    Expression.prototype.toString = function() {
      return this.expression;
    };

    return Expression;

  })();

  _getData = function(scope, path, loadMode, dataCtx) {
    var retValue;
    retValue = scope.get(path, loadMode, dataCtx);
    if (retValue === void 0 && (dataCtx != null ? dataCtx.vars : void 0)) {
      retValue = dataCtx.vars[path];
    }
    return retValue;
  };

  cola.registerTypeResolver("validator", function(config) {
    if (!(config && config.$type)) {
      return;
    }
    return cola[cola.util.capitalize(config.$type) + "Validator"];
  });

  cola.registerTypeResolver("validator", function(config) {
    if (typeof config === "function") {
      return cola.CustomValidator;
    }
  });

  cola.Validator = (function(superClass) {
    extend(Validator, superClass);

    function Validator() {
      return Validator.__super__.constructor.apply(this, arguments);
    }

    Validator.attributes = {
      message: null,
      messageType: {
        defaultValue: "error",
        "enum": ["error", "warning", "info"]
      },
      disabled: null,
      validateEmptyValue: null
    };

    Validator.prototype._getDefaultMessage = function(data) {
      return "\"" + data + "\" is not a valid value.";
    };

    Validator.prototype._parseValidResult = function(result, data) {
      var text;
      if (typeof result === "boolean") {
        if (result) {
          result = null;
        } else {
          text = this._message;
          if (text == null) {
            text = this._getDefaultMessage(data);
          }
          result = {
            type: this._messageType,
            text: text
          };
        }
      } else if (result && typeof result === "string") {
        result = {
          type: this._messageType,
          text: result
        };
      }
      return result;
    };

    Validator.prototype.validate = function(data) {
      var result;
      if (!this._validateEmptyValue) {
        if (!((data != null) && data !== "")) {
          return;
        }
      }
      result = this._validate(data);
      return this._parseValidResult(result, data);
    };

    return Validator;

  })(cola.Definition);

  cola.RequiredValidator = (function(superClass) {
    extend(RequiredValidator, superClass);

    function RequiredValidator() {
      return RequiredValidator.__super__.constructor.apply(this, arguments);
    }

    RequiredValidator.attributes = {
      validateEmptyValue: {
        defaultValue: true
      },
      trim: {
        defaultValue: true
      }
    };

    RequiredValidator.prototype._getDefaultMessage = function(data) {
      return cola.resource("cola.validator.error.required", data);
    };

    RequiredValidator.prototype._validate = function(data) {
      if (!(typeof data === "string")) {
        return data != null;
      }
      if (this._trim) {
        data = cola.util.trim(data);
      }
      return !!data;
    };

    return RequiredValidator;

  })(cola.Validator);

  cola.NumberValidator = (function(superClass) {
    extend(NumberValidator, superClass);

    function NumberValidator() {
      return NumberValidator.__super__.constructor.apply(this, arguments);
    }

    NumberValidator.attributes = {
      min: null,
      minInclude: {
        defaultValue: true
      },
      max: null,
      maxInclude: {
        defaultValue: true
      }
    };

    NumberValidator.prototype._getDefaultMessage = function(data) {
      return cola.resource("cola.validator.error.number", data);
    };

    NumberValidator.prototype._validate = function(data) {
      var result;
      result = true;
      if (this._min != null) {
        result = this._minInclude ? data >= this._min : data > this._min;
      }
      if (result && (this._max != null)) {
        result = this._maxInclude ? data <= this._max : data < this._max;
      }
      return result;
    };

    return NumberValidator;

  })(cola.Validator);

  cola.LengthValidator = (function(superClass) {
    extend(LengthValidator, superClass);

    function LengthValidator() {
      return LengthValidator.__super__.constructor.apply(this, arguments);
    }

    LengthValidator.attributes = {
      min: null,
      max: null
    };

    LengthValidator.prototype._getDefaultMessage = function(data) {
      return cola.resource("cola.validator.error.length", data);
    };

    LengthValidator.prototype._validate = function(data) {
      var len, result;
      if (typeof data === "string" || typeof data === "number") {
        result = true;
        len = (data + "").length;
        if (this._min != null) {
          result = len >= this._min;
        }
        if (result && (this._max != null)) {
          result = len <= this._max;
        }
        return result;
      }
      return true;
    };

    return LengthValidator;

  })(cola.Validator);

  cola.RegExpValidator = (function(superClass) {
    extend(RegExpValidator, superClass);

    function RegExpValidator() {
      return RegExpValidator.__super__.constructor.apply(this, arguments);
    }

    RegExpValidator.attributes = {
      regExp: null,
      mode: {
        defaultValue: "white",
        "enum": ["white", "black"]
      }
    };

    RegExpValidator.prototype._getDefaultMessage = function(data) {
      return cola.resource("cola.validator.error.regExp", data);
    };

    RegExpValidator.prototype._validate = function(data) {
      var regExp, result;
      regExp = this._regExp;
      if (regExp && typeof data === "string") {
        if (!regExp instanceof RegExp) {
          regExp = new RegExp(regExp);
        }
        result = true;
        result = !!data.match(regExp);
        if (this._mode === "black") {
          result = !result;
        }
        return result;
      }
      return true;
    };

    return RegExpValidator;

  })(cola.Validator);

  cola.EmailValidator = (function(superClass) {
    extend(EmailValidator, superClass);

    function EmailValidator() {
      return EmailValidator.__super__.constructor.apply(this, arguments);
    }

    EmailValidator.prototype._getDefaultMessage = function(data) {
      return cola.resource("cola.validator.error.email", data);
    };

    EmailValidator.prototype._validate = function(data) {
      if (typeof data === "string") {
        return !!data.match(/^([a-z0-9]*[-_\.]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\.][a-z]{2,3}([\.][a-z]{2})?$/i);
      }
      return true;
    };

    return EmailValidator;

  })(cola.Validator);

  cola.UrlValidator = (function(superClass) {
    extend(UrlValidator, superClass);

    function UrlValidator() {
      return UrlValidator.__super__.constructor.apply(this, arguments);
    }

    UrlValidator.prototype._getDefaultMessage = function(data) {
      return cola.resource("cola.validator.error.email", data);
    };

    UrlValidator.prototype._validate = function(data) {
      if (typeof data === "string") {
        return !!data.match(/^(https?:\/\/(?:www\.|(?!www))[^\s\.]+\.[^\s]{2,}|www\.[^\s]+\.[^\s]{2,})/i);
      }
      return true;
    };

    return UrlValidator;

  })(cola.Validator);

  cola.AsyncValidator = (function(superClass) {
    extend(AsyncValidator, superClass);

    function AsyncValidator() {
      return AsyncValidator.__super__.constructor.apply(this, arguments);
    }

    AsyncValidator.attributes = {
      async: {
        defaultValue: true
      }
    };

    AsyncValidator.prototype.validate = function(data, callback) {
      var result;
      if (!this._validateEmptyValue) {
        if (!((data != null) && data !== "")) {
          return;
        }
      }
      if (this._async) {
        result = this._validate(data, {
          complete: (function(_this) {
            return function(success, result) {
              if (success) {
                result = _this._parseValidResult(result);
              }
              cola.callback(callback, success, result);
            };
          })(this)
        });
      } else {
        result = this._validate(data);
        result = this._parseValidResult(result);
        cola.callback(callback, true, result);
      }
      return result;
    };

    return AsyncValidator;

  })(cola.Validator);

  cola.AjaxValidator = (function(superClass) {
    extend(AjaxValidator, superClass);

    AjaxValidator.attributes = {
      url: null,
      method: null,
      ajaxOptions: null,
      data: null
    };

    function AjaxValidator(config) {
      AjaxValidator.__super__.constructor.call(this, config);
      this._ajaxService = new cola.AjaxService();
    }

    AjaxValidator.prototype._validate = function(data, callback) {
      var invoker, options, p, realSendData, sendData, v;
      sendData = this._data;
      if ((sendData == null) || sendData === ":data") {
        sendData = data;
      } else if (typeof sendData === "object") {
        realSendData = {};
        for (p in sendData) {
          v = sendData[p];
          if (v === ":data") {
            v = data;
          }
          realSendData[p] = v;
        }
        sendData = realSendData;
      }
      options = {
        async: !!callback,
        url: this._url,
        data: sendData,
        method: this._method,
        ajaxOptions: this._ajaxOptions
      };
      invoker = new cola.AjaxServiceInvoker(this._ajaxService, options);
      if (callback) {
        return invoker.invokeAsync(callback);
      } else {
        return invoker.invokeSync();
      }
    };

    return AjaxValidator;

  })(cola.AsyncValidator);

  cola.CustomValidator = (function(superClass) {
    extend(CustomValidator, superClass);

    CustomValidator.attributes = {
      async: {
        defaultValue: false
      },
      validateEmptyValue: {
        defaultValue: true
      },
      func: null
    };

    function CustomValidator(config) {
      if (typeof config === "function") {
        CustomValidator.__super__.constructor.call(this);
        this.set({
          func: config,
          async: cola.util.parseFunctionArgs(config).length > 1
        });
      } else {
        CustomValidator.__super__.constructor.call(this, config);
      }
    }

    CustomValidator.prototype._validate = function(data, callback) {
      if (this._async && callback) {
        if (this._func) {
          this._func(data, callback);
        } else {
          cola.callback(callback, true);
        }
      } else {
        return typeof this._func === "function" ? this._func(data) : void 0;
      }
    };

    return CustomValidator;

  })(cola.AsyncValidator);

  cola.DataType = (function(superClass) {
    extend(DataType, superClass);

    function DataType() {
      return DataType.__super__.constructor.apply(this, arguments);
    }

    return DataType;

  })(cola.Definition);

  cola.BaseDataType = (function(superClass) {
    extend(BaseDataType, superClass);

    function BaseDataType() {
      return BaseDataType.__super__.constructor.apply(this, arguments);
    }

    return BaseDataType;

  })(cola.DataType);

  cola.StringDataType = (function(superClass) {
    extend(StringDataType, superClass);

    function StringDataType() {
      return StringDataType.__super__.constructor.apply(this, arguments);
    }

    StringDataType.prototype.toText = function(value) {
      if (value != null) {
        return value + "";
      } else {
        return "";
      }
    };

    StringDataType.prototype.parse = function(text) {
      return text;
    };

    return StringDataType;

  })(cola.BaseDataType);

  cola.NumberDataType = (function(superClass) {
    extend(NumberDataType, superClass);

    function NumberDataType() {
      return NumberDataType.__super__.constructor.apply(this, arguments);
    }

    NumberDataType.attributes = {
      isInteger: null
    };

    NumberDataType.prototype.parse = function(text) {
      var n;
      if (!text) {
        return 0;
      }
      if (typeof text === "number") {
        if (this._isInteger) {
          return Math.round(text);
        } else {
          return text;
        }
      }
      if (this._isInteger) {
        n = Math.round(parseInt(text, 10));
      } else {
        n = parseFloat(text, 10);
      }
      if (isNaN(n)) {
        return 0;
      } else {
        return n;
      }
    };

    return NumberDataType;

  })(cola.BaseDataType);

  cola.BooleanDataType = (function(superClass) {
    extend(BooleanDataType, superClass);

    function BooleanDataType() {
      return BooleanDataType.__super__.constructor.apply(this, arguments);
    }

    BooleanDataType.prototype.parse = function(text) {
      if (!text) {
        return false;
      }
      if (typeof text === "boolean") {
        return text;
      }
      if (["true", "on", "yes", "y", "1"].indexOf((text + "").toLowerCase()) > -1) {
        return true;
      }
      return false;
    };

    return BooleanDataType;

  })(cola.BaseDataType);

  cola.DateDataType = (function(superClass) {
    extend(DateDataType, superClass);

    function DateDataType() {
      return DateDataType.__super__.constructor.apply(this, arguments);
    }

    DateDataType.prototype.parse = function(text) {
      var xDate;
      if (!text) {
        return new Date(NaN);
      }
      xDate = new XDate(text);
      return xDate.toDate();
    };

    return DateDataType;

  })(cola.BaseDataType);

  cola.JSONDataType = (function(superClass) {
    extend(JSONDataType, superClass);

    function JSONDataType() {
      return JSONDataType.__super__.constructor.apply(this, arguments);
    }

    JSONDataType.prototype.toText = function(value) {
      return JSON.stringify(value);
    };

    JSONDataType.prototype.parse = function(text) {
      if (typeof text === "string") {
        return JSON.parse(text);
      } else {
        return text;
      }
    };

    return JSONDataType;

  })(cola.DataType);


  /*
  EntityDataType
   */

  cola.EntityDataType = (function(superClass) {
    extend(EntityDataType, superClass);

    EntityDataType.attributes = {
      properties: {
        setter: function(properties) {
          var config, l, len1, property, results, results1;
          this._properties.clear();
          if (properties instanceof Array) {
            results = [];
            for (l = 0, len1 = properties.length; l < len1; l++) {
              property = properties[l];
              results.push(this.addProperty(property));
            }
            return results;
          } else {
            results1 = [];
            for (property in properties) {
              config = properties[property];
              if (config) {
                if (!(config instanceof cola.Property)) {
                  config.property = property;
                }
                results1.push(this.addProperty(config));
              } else {
                results1.push(void 0);
              }
            }
            return results1;
          }
        }
      }
    };

    EntityDataType.events = {
      beforeCurrentChange: null,
      currentChange: null,
      beforeDataChange: null,
      dataChange: null,
      beforeEntityInsert: null,
      entityInsert: null,
      beforeEntityDelete: null,
      entityDelete: null
    };

    function EntityDataType(config) {
      this._properties = new cola.util.KeyedArray();
      EntityDataType.__super__.constructor.call(this, config);
    }

    EntityDataType.prototype.addProperty = function(property) {
      if (!(property instanceof cola.Property)) {
        if (typeof property === "string") {
          property = new cola.Property({
            property: property
          });
        } else {
          property = new cola.Property(property);
        }
      } else if (property._owner && property._owner !== this) {
        throw new cola.Exception("Property(" + property._property + ") is already belongs to anthor DataType.");
      }
      if (this._properties.get(property._property)) {
        this.removeProperty(property._property);
      }
      this._properties.add(property._property, property);
      property._owner = this;
      return property;
    };

    EntityDataType.prototype.removeProperty = function(property) {
      if (property instanceof cola.Property) {
        this._properties.remove(property._property);
      } else {
        property = this._properties.remove(property);
      }
      delete property._owner;
      return property;
    };

    EntityDataType.prototype.getProperty = function(path) {
      var i, part1, part2, prop;
      i = path.indexOf(".");
      if (i > 0) {
        part1 = path.substring(0, i);
        part2 = path.substring(i + 1);
        prop = this._getProperty(part1);
        if (prop != null ? prop._dataType : void 0) {
          return prop != null ? prop._dataType.getProperty(part2) : void 0;
        }
      } else {
        return this._getProperty(path);
      }
    };

    EntityDataType.prototype._getProperty = function(property) {
      return this._properties.get(property);
    };

    EntityDataType.prototype.getProperties = function() {
      return this._properties;
    };

    return EntityDataType;

  })(cola.DataType);

  cola.DataType.dataTypeSetter = function(dataType) {
    var name, scope;
    if (typeof dataType === "string") {
      name = dataType;
      scope = this._scope;
      if (scope) {
        dataType = scope.dataType(name);
      } else {
        dataType = cola.DataType.defaultDataTypes[name];
      }
      if (!dataType) {
        throw new cola.Exception("Unrecognized DataType \"" + name + "\".");
      }
    } else if ((dataType != null) && !(dataType instanceof cola.DataType)) {
      dataType = new cola.EntityDataType(dataType);
    }
    this._dataType = dataType || null;
  };

  cola.Property = (function(superClass) {
    extend(Property, superClass);

    function Property() {
      return Property.__super__.constructor.apply(this, arguments);
    }

    Property.attributes = {
      property: {
        readOnlyAfterCreate: true
      },
      name: {
        setter: function(name) {
          this._name = name;
          if (this._property == null) {
            this._property = name;
          }
        }
      },
      owner: {
        readOnly: true
      },
      caption: null,
      dataType: {
        setter: cola.DataType.dataTypeSetter
      },
      description: null,
      provider: {
        setter: function(provider) {
          if ((provider != null) && !(provider instanceof cola.Provider)) {
            provider = new cola.Provider(provider);
          }
          this._provider = provider;
        }
      },
      defaultValue: null,
      aggregated: {
        readOnlyAfterCreate: true
      },
      validators: {
        setter: function(validators) {
          var addValidator, l, len1, validator;
          addValidator = (function(_this) {
            return function(validator) {
              if (!(validator instanceof cola.Validator)) {
                validator = cola.create("validator", validator, cola.Validator);
              }
              _this._validators.push(validator);
            };
          })(this);
          delete this._validators;
          if (validators) {
            this._validators = [];
            if (typeof validators === "string") {
              validator = cola.create("validator", validators, cola.Validator);
              addValidator(validator);
            } else if (validators instanceof Array) {
              for (l = 0, len1 = validators.length; l < len1; l++) {
                validator = validators[l];
                addValidator(validator);
              }
            } else {
              addValidator(validators);
            }
          }
        }
      },
      rejectInvalidValue: null
    };

    Property.events = {
      beforeWrite: null,
      write: null,
      beforeLoad: null,
      loaded: null
    };

    return Property;

  })(cola.Definition);

  cola.DataType.jsonToEntity = function(json, dataType, aggregated, pageSize) {
    var entityList;
    if (aggregated === void 0) {
      if (json instanceof Array) {
        aggregated = true;
      } else if (typeof json === "object" && json.hasOwnProperty("$data")) {
        aggregated = json.$data instanceof Array;
      } else {
        aggregated = false;
      }
    }
    if (aggregated) {
      entityList = new cola.EntityList(null, dataType);
      if (pageSize) {
        entityList.pageSize = pageSize;
      }
      entityList.fillData(json);
      return entityList;
    } else {
      if (json instanceof Array) {
        throw new cola.Exception("Unmatched DataType. expect \"Object\" but \"Array\".");
      }
      return new cola.Entity(json, dataType);
    }
  };

  cola.DataType.jsonToData = function(json, dataType, aggregated, pageSize) {
    var result;
    if (dataType instanceof cola.StringDataType && typeof json !== "string" || dataType instanceof cola.BooleanDataType && typeof json !== "boolean" || dataType instanceof cola.NumberDataType && typeof json !== "number" || dataType instanceof cola.DateDataType && !(json instanceof Date)) {
      result = dataType.parse(json);
    } else if (dataType instanceof cola.EntityDataType) {
      result = cola.DataType.jsonToEntity(json, dataType, aggregated, pageSize);
    } else if (dataType && typeof json === "object") {
      result = dataType.parse(json);
    } else {
      result = json;
    }
    return result;
  };

  cola.DataType.defaultDataTypes = defaultDataTypes = {
    "string": new cola.StringDataType({
      name: "string"
    }),
    "int": new cola.NumberDataType({
      name: "int",
      isInteger: true
    }),
    "float": new cola.NumberDataType({
      name: "float"
    }),
    "boolean": new cola.BooleanDataType({
      name: "boolean"
    }),
    "date": new cola.DateDataType({
      name: "date"
    }),
    "json": new cola.JSONDataType({
      name: "json"
    }),
    "entity": new cola.EntityDataType({
      name: "entity"
    })
  };

  defaultDataTypes["number"] = defaultDataTypes["float"];

  _getEntityPath = function(markNoncurrent) {
    var lastEntity, parent, part, path, self;
    if (!markNoncurrent && this._pathCache) {
      return this._pathCache;
    }
    parent = this.parent;
    if (parent == null) {
      return;
    }
    path = [];
    self = this;
    while (parent != null) {
      if (parent instanceof _EntityList) {
        lastEntity = self;
      }
      part = self._parentProperty;
      if (part) {
        if (markNoncurrent && self instanceof _EntityList) {
          if (markNoncurrent === "always" || lastEntity && self.current !== lastEntity) {
            path.push("!" + part);
          } else {
            path.push(part);
          }
        } else {
          path.push(part);
        }
      }
      self = parent;
      parent = parent.parent;
    }
    path = path.reverse();
    if (!markNoncurrent) {
      this._pathCache = path;
    }
    return path;
  };

  _watch = function(path, watcher) {
    var holder;
    if (path instanceof Function) {
      watcher = path;
      path = "*";
    }
    if (this._watchers == null) {
      this._watchers = {};
    }
    holder = this._watchers[path];
    if (!holder) {
      this._watchers[path] = {
        path: path.split("."),
        watchers: [watcher]
      };
    } else {
      holder.watchers.push(watcher);
    }
  };

  _unwatch = function(path, watcher) {
    var holder, i, l, len1, ref, w, watchers;
    if (!this._watchers) {
      return;
    }
    if (path instanceof Function) {
      watcher = path;
      path = "*";
    }
    watchers = this._watchers;
    if (!watcher) {
      delete watchers[path];
    } else {
      holder = watchers[path];
      if (holder) {
        ref = holder.watchers;
        for (i = l = 0, len1 = ref.length; l < len1; i = ++l) {
          w = ref[i];
          if (w === watcher) {
            holder.watchers.splice(i, 1);
            break;
          }
        }
        if (!holder.watchers.length) {
          delete watchers[path];
        }
      }
    }
  };

  _triggerWatcher = function(path, type, arg) {
    var holder, i, l, len1, len2, o, p, pv, ref, ref1, s, shouldTrigger, watch;
    if (this._watchers) {
      ref = this._watchers;
      for (p in ref) {
        holder = ref[p];
        shouldTrigger = false;
        if (p === "**") {
          shouldTrigger = true;
        } else if (p === "*") {
          shouldTrigger = path.length < 2;
        } else {
          pv = holder.path;
          if (pv.length >= path.length) {
            shouldTrigger = true;
            for (i = l = 0, len1 = pv.length; l < len1; i = ++l) {
              s = pv[i];
              if (i === pv.length - 1) {
                if (s === "**") {
                  break;
                } else if (s === "*") {
                  shouldTrigger = i === path.length - 1;
                  break;
                }
              }
              if (s !== path[i]) {
                shouldTrigger = false;
                break;
              }
            }
          }
        }
        if (shouldTrigger) {
          ref1 = holder.watchers;
          for (o = 0, len2 = ref1.length; o < len2; o++) {
            watch = ref1[o];
            watch.call(this, path, type, arg);
          }
        }
      }
    }
    if (this.parent) {
      if (this._parentProperty) {
        path.unshift(this._parentProperty);
      }
      this.parent._triggerWatcher(path, type, arg);
    }
  };

  _matchValue = function(value, propFilter) {
    if (propFilter.strict) {
      if (!propFilter.caseSensitive && typeof propFilter.value === "string") {
        return (value + "").toLowerCase() === propFilter.value;
      } else {
        return value === propFilter.value;
      }
    } else {
      if (!propFilter.caseSensitive) {
        return (value + "").toLowerCase().indexOf(propFilter.value) > -1;
      } else {
        return (value + "").indexOf(propFilter.value) > -1;
      }
    }
  };

  cola._trimCriteria = function(criteria, option) {
    var prop, propFilter;
    if (option == null) {
      option = {};
    }
    if (criteria == null) {
      return criteria;
    }
    if (cola.util.isSimpleValue(criteria)) {
      if (!option.caseSensitive) {
        criteria = (criteria + "").toLowerCase();
      }
      criteria = {
        "$": {
          value: criteria,
          caseSensitive: option.caseSensitive,
          strict: option.strict
        }
      };
    } else if (typeof criteria === "object") {
      for (prop in criteria) {
        propFilter = criteria[prop];
        if (typeof propFilter === "string") {
          criteria[prop] = {
            value: propFilter.toLowerCase(),
            caseSensitive: option.caseSensitive,
            strict: option.strict
          };
        } else {
          if (propFilter.caseSensitive == null) {
            propFilter.caseSensitive = option.caseSensitive;
          }
          if (!propFilter.caseSensitive && typeof propFilter.value === "string") {
            propFilter.value = propFilter.value.toLowerCase();
          }
          if (propFilter.strict == null) {
            propFilter.strict = option.strict;
          }
          if (!propFilter.strict) {
            propFilter.value = propFilter.value ? propFilter.value + "" : "";
          }
        }
      }
    }
    return criteria;
  };

  _filterCollection = function(collection, criteria, option) {
    var filtered;
    if (option == null) {
      option = {};
    }
    if (!collection) {
      return null;
    }
    filtered = [];
    filtered.$origin = collection.$origin || collection;
    if (!option.mode) {
      option.mode = collection instanceof cola.EntityList ? "entity" : "json";
    }
    cola.each(collection, function(item) {
      var children;
      children = option.deep ? [] : null;
      if ((criteria == null) || _filterEntity(item, criteria, option, children)) {
        filtered.push(item);
        if (option.one) {
          return false;
        }
      }
      if (children) {
        Array.prototype.push.apply(filtered, children);
      }
    });
    return filtered;
  };

  _filterEntity = function(entity, criteria, option, children) {
    var _searchChildren, data, matches, p, prop, propFilter, v;
    if (option == null) {
      option = {};
    }
    _searchChildren = function(value) {
      var r;
      if (option.mode === "entity") {
        if (value instanceof cola.EntityList) {
          r = _filterCollection(value, criteria, option);
          Array.prototype.push.apply(children, r);
        } else if (value instanceof cola.Entity) {
          r = [];
          _filterEntity(value, criteria, option, r);
          Array.prototype.push.apply(children, r);
        }
      } else {
        if (typeof value === "array") {
          r = _filterCollection(value, criteria, option);
          Array.prototype.push.apply(children, r);
        } else if (typeof value === "object" && !(value instanceof Date)) {
          r = [];
          _filterEntity(value, criteria, option, r);
          Array.prototype.push.apply(children, r);
        }
      }
    };
    if (!entity) {
      return false;
    }
    if (!option.mode) {
      option.mode = entity instanceof cola.Entity ? "entity" : "json";
    }
    matches = false;
    if (criteria == null) {
      matches = true;
    } else if (typeof criteria === "object") {
      if (cola.util.isSimpleValue(entity)) {
        if (criteria.$) {
          matches = _matchValue(v, criteria.$);
        }
      } else {
        for (prop in criteria) {
          propFilter = criteria[prop];
          data = null;
          if (prop === "$") {
            if (option.mode === "entity") {
              data = entity._data;
            } else {
              data = entity;
            }
            for (p in data) {
              v = data[p];
              if (_matchValue(v, propFilter)) {
                matches = true;
                if (!children) {
                  break;
                }
              }
            }
            if (matches && !children) {
              break;
            }
          } else if (option.mode === "entity") {
            if (_matchValue(entity.get(prop), propFilter)) {
              matches = true;
              if (!children) {
                break;
              }
            }
          } else {
            if (_matchValue(entity[prop], propFilter)) {
              matches = true;
              if (!children) {
                break;
              }
            }
          }
        }
      }
    } else if (typeof criteria === "function") {
      matches = criteria(entity, option);
    }
    if (children && (!option.one || !matches)) {
      if (data == null) {
        if (option.mode === "entity") {
          data = entity._data;
        } else {
          data = entity;
        }
      }
      for (p in data) {
        v = data[p];
        _searchChildren(v);
      }
    }
    return matches;
  };

  _sortCollection = function(collection, comparator, caseSensitive) {
    var c, comparatorFunc, comparatorProps, l, len1, origin, part, prop, propDesc, ref;
    if (!collection) {
      return null;
    }
    if ((comparator == null) || comparator === "$none") {
      return collection;
    }
    if (collection instanceof cola.EntityList) {
      origin = collection;
      collection = collection.toArray();
      collection.$origin = origin;
    }
    if (comparator) {
      if (comparator === "$reverse") {
        return collection.reverse();
      } else if (typeof comparator === "string") {
        comparatorProps = [];
        ref = comparator.split(",");
        for (l = 0, len1 = ref.length; l < len1; l++) {
          part = ref[l];
          c = part.charCodeAt(0);
          propDesc = false;
          if (c === 43) {
            prop = part.substring(1);
          } else if (c === 45) {
            prop = part.substring(1);
            propDesc = true;
          } else {
            prop = part;
          }
          comparatorProps.push({
            prop: prop,
            desc: propDesc
          });
        }
        comparator = function(item1, item2) {
          var comparatorProp, len2, o, result, value1, value2;
          for (o = 0, len2 = comparatorProps.length; o < len2; o++) {
            comparatorProp = comparatorProps[o];
            value1 = null;
            value2 = null;
            prop = comparatorProp.prop;
            if (prop) {
              if (prop === "$random") {
                return Math.random() * 2 - 1;
              } else {
                if (item1 instanceof cola.Entity) {
                  value1 = item1.get(prop);
                } else if (cola.util.isSimpleValue(item1)) {
                  value1 = item1;
                } else {
                  value1 = item1[prop];
                }
                if (!caseSensitive && typeof value1 === "string") {
                  value1 = value1.toLowerCase();
                }
                if (item2 instanceof cola.Entity) {
                  value2 = item2.get(prop);
                } else if (cola.util.isSimpleValue(item2)) {
                  value2 = item2;
                } else {
                  value2 = item2[prop];
                }
                if (!caseSensitive && typeof value2 === "string") {
                  value2 = value2.toLowerCase();
                }
                result = 0;
                if (value1 == null) {
                  result = -1;
                } else if (value2 == null) {
                  result = 1;
                } else if (value1 > value2) {
                  result = 1;
                } else if (value1 < value2) {
                  result = -1;
                }
                if (result !== 0) {
                  if (comparatorProp.desc) {
                    return 0 - result;
                  } else {
                    return result;
                  }
                }
              }
            } else {
              result = 0;
              if (item1 == null) {
                result = -1;
              } else if (item2 == null) {
                result = 1;
              } else if (item1 > item2) {
                result = 1;
              } else if (item1 < item2) {
                result = -1;
              }
              if (result !== 0) {
                if (comparatorProp.desc) {
                  return 0 - result;
                } else {
                  return result;
                }
              }
            }
          }
          return 0;
        };
      }
    } else {
      comparator = function(item1, item2) {
        var result;
        result = 0;
        if (!caseSensitive) {
          if (typeof item1 === "string") {
            item1 = item1.toLowerCase();
          }
          if (typeof item2 === "string") {
            item2 = item2.toLowerCase();
          }
        }
        if (item1 == null) {
          result = -1;
        } else if (item2 == null) {
          result = 1;
        } else if (item1 > item2) {
          result = 1;
        } else if (item1 < item2) {
          result = -1;
        }
        return result;
      };
    }
    comparatorFunc = function(item1, item2) {
      return comparator(item1, item2);
    };
    return collection.sort(comparatorFunc);
  };

  cola.Entity = (function() {
    Entity.STATE_NONE = "none";

    Entity.STATE_NEW = "new";

    Entity.STATE_MODIFIED = "modified";

    Entity.STATE_DELETED = "deleted";

    Entity.prototype.state = Entity.STATE_NONE;

    Entity.prototype._disableObserverCount = 0;

    Entity.prototype._disableWriteObservers = 0;

    function Entity(data, dataType) {
      this.id = cola.uniqueId();
      this.timestamp = cola.sequenceNo();
      this.dataType = dataType;
      this._data = {};
      if (data != null) {
        this._disableWriteObservers++;
        this.set(data);
        this._disableWriteObservers--;
      }
    }

    Entity.prototype.hasValue = function(prop) {
      var ref;
      return this._data.hasOwnProperty(prop) || (((ref = this.dataType) != null ? ref.getProperty(prop) : void 0) != null);
    };

    Entity.prototype.get = function(prop, loadMode, context) {
      var callback;
      if (loadMode == null) {
        loadMode = "async";
      }
      if (loadMode && (typeof loadMode === "function" || typeof loadMode === "object")) {
        callback = loadMode;
        loadMode = "async";
      }
      if (prop.indexOf(".") > 0) {
        return _evalDataPath(this, prop, false, loadMode, callback, context);
      } else {
        return this._get(prop, loadMode, callback, context);
      }
    };

    Entity.prototype._get = function(prop, loadMode, callback, context) {
      var callbackProcessed, loadData, property, provider, providerInvoker, ref;
      loadData = function(provider) {
        var notifyArg, providerInvoker, retValue;
        retValue = void 0;
        providerInvoker = provider.getInvoker({
          data: this
        });
        if (loadMode === "sync") {
          retValue = providerInvoker.invokeSync();
          retValue = this._set(prop, retValue);
          if (retValue && (retValue instanceof cola.EntityList || retValue instanceof cola.Entity)) {
            retValue._providerInvoker = providerInvoker;
          }
        } else if (loadMode === "async") {
          if (context) {
            context.unloaded = true;
            if (context.providerInvokers == null) {
              context.providerInvokers = [];
            }
            context.providerInvokers.push(providerInvoker);
          }
          this._data[prop] = providerInvoker;
          notifyArg = {
            data: this,
            property: prop
          };
          this._notify(cola.constants.MESSAGE_LOADING_START, notifyArg);
          providerInvoker.invokeAsync({
            complete: (function(_this) {
              return function(success, result) {
                _this._notify(cola.constants.MESSAGE_LOADING_END, notifyArg);
                if (_this._data[prop] !== providerInvoker) {
                  success = false;
                }
                if (success) {
                  result = _this._set(prop, result);
                  retValue = result;
                  if (result && (result instanceof cola.EntityList || result instanceof cola.Entity)) {
                    result._providerInvoker = providerInvoker;
                  }
                } else {
                  _this._set(prop, null);
                }
                if (callback) {
                  cola.callback(callback, success, result);
                }
              };
            })(this)
          });
        } else {
          cola.callback(callback, true, void 0);
        }
        return retValue;
      };
      property = (ref = this.dataType) != null ? ref.getProperty(prop) : void 0;
      value = this._data[prop];
      if (value === void 0) {
        if (property) {
          provider = property.get("provider");
          if (context != null) {
            context.unloaded = true;
          }
          if (provider && provider._loadMode === "lazy") {
            value = loadData.call(this, provider);
            callbackProcessed = true;
          }
        }
      } else if (value instanceof cola.Provider) {
        value = loadData.call(this, value);
        callbackProcessed = true;
      } else if (value instanceof cola.AjaxServiceInvoker) {
        providerInvoker = value;
        if (loadMode === "sync") {
          value = providerInvoker.invokeSync();
          value = this._set(prop, value);
        } else if (loadMode === "async") {
          if (callback) {
            providerInvoker.callbacks.push(callback);
          }
          callbackProcessed = true;
          value = void 0;
        } else {
          value = void 0;
        }
        if (context) {
          context.unloaded = true;
          if (context.providerInvokers == null) {
            context.providerInvokers = [];
          }
          context.providerInvokers.push(providerInvoker);
        }
      }
      if (callback && !callbackProcessed) {
        cola.callback(callback, true, value);
      }
      return value;
    };

    Entity.prototype.set = function(prop, value, context) {
      var config;
      if (typeof prop === "string") {
        _setValue(this, prop, value, context);
      } else if (prop && (typeof prop === "object")) {
        config = prop;
        for (prop in config) {
          if (prop.charAt(0) === "$") {
            continue;
          }
          this.set(prop, config[prop]);
        }
      }
      return this;
    };

    Entity.prototype._jsonToEntity = function(value, dataType, aggregated, provider) {
      var result;
      result = cola.DataType.jsonToEntity(value, dataType, aggregated, provider != null ? provider._pageSize : void 0);
      if (result && provider) {
        result._providerInvoker = provider.getInvoker({
          data: this
        });
      }
      return result;
    };

    Entity.prototype._set = function(prop, value) {
      var actualType, changed, convert, dataType, expectedType, item, l, len1, len2, len3, matched, message, messages, o, oldValue, property, provider, q, ref, ref1, ref2, ref3, ref4, validator;
      oldValue = this._data[prop];
      property = (ref = this.dataType) != null ? ref.getProperty(prop) : void 0;
      if (value != null) {
        if (value instanceof cola.Provider) {
          changed = oldValue !== void 0;
        } else {
          if (property) {
            dataType = property._dataType;
            provider = property._provider;
          }
          if (dataType) {
            if (value != null) {
              if (dataType instanceof cola.StringDataType && typeof value !== "string" || dataType instanceof cola.BooleanDataType && typeof value !== "boolean" || dataType instanceof cola.NumberDataType && typeof value !== "number" || dataType instanceof cola.DateDataType && !(value instanceof Date)) {
                value = dataType.parse(value);
              } else if (dataType instanceof cola.EntityDataType) {
                matched = true;
                if (value instanceof _Entity) {
                  matched = value.dataType === dataType && !property._aggregated;
                } else if (value instanceof _EntityList) {
                  matched = value.dataType === dataType && property._aggregated;
                } else {
                  value = this._jsonToEntity(value, dataType, property._aggregated, provider);
                }
                if (!matched) {
                  expectedType = dataType.get("name");
                  actualType = ((ref1 = value.dataType) != null ? ref1.get("name") : void 0) || "undefined";
                  if (property._aggregated) {
                    expectedType = "[" + expectedType + "]";
                  }
                  if (value instanceof cola.EntityList) {
                    actualType = "[" + actualType + "]";
                  }
                  throw new cola.Exception("Unmatched DataType. expect \"" + expectedType + "\" but \"" + actualType + "\".");
                }
              } else {
                value = dataType.parse(value);
              }
            }
          } else if (typeof value === "object" && (value != null)) {
            if (value instanceof Array) {
              convert = true;
              if (value.length > 0) {
                item = value[0];
                if (cola.util.isSimpleValue(item)) {
                  convert = false;
                }
              }
              if (convert) {
                value = this._jsonToEntity(value, null, true, provider);
              }
            } else if (value.hasOwnProperty("$data")) {
              value = this._jsonToEntity(value, null, true, provider);
            } else if (value instanceof Date) {

            } else if (!(value instanceof _Entity || value instanceof _EntityList)) {
              value = this._jsonToEntity(value, null, false, provider);
            }
          }
          changed = oldValue !== value;
        }
      } else {
        changed = oldValue !== value;
      }
      if (changed) {
        if (property) {
          if (property._validators && property._rejectInvalidValue) {
            messages = null;
            ref2 = property._validators;
            for (l = 0, len1 = ref2.length; l < len1; l++) {
              validator = ref2[l];
              if ((value != null) || validator instanceof cola.RequiredValidator) {
                if (!(validator._disabled && validator instanceof cola.AsyncValidator && validator.get("async"))) {
                  message = validator.validate(value);
                  if (message) {
                    if (messages == null) {
                      messages = [];
                    }
                    if (message instanceof Array) {
                      Array.prototype.push.apply(messages, message);
                    } else {
                      messages.push(message);
                    }
                  }
                }
              }
            }
            if (messages) {
              for (o = 0, len2 = messages.length; o < len2; o++) {
                message = messages[o];
                if (message === "error") {
                  throw new cola.Exception(message.text);
                }
              }
            }
          }
        }
        if (this._disableWriteObservers === 0) {
          if ((oldValue != null) && (oldValue instanceof _Entity || oldValue instanceof _EntityList)) {
            oldValue._setDataModel(null);
            delete oldValue.parent;
            delete oldValue._parentProperty;
          }
          if (this.state === _Entity.STATE_NONE) {
            this.setState(_Entity.STATE_MODIFIED);
          }
        }
        this._data[prop] = value;
        if ((value != null) && (value instanceof _Entity || value instanceof _EntityList)) {
          if (value.parent && value.parent !== this) {
            throw new cola.Exception("Entity/EntityList is already belongs to another owner. \"" + prop + "\"");
          }
          value.parent = this;
          value._parentProperty = prop;
          value._setDataModel(this._dataModel);
          value._onPathChange();
          this._mayHasSubEntity = true;
        }
        this.timestamp = cola.sequenceNo();
        if (this._disableWriteObservers === 0) {
          this._notify(cola.constants.MESSAGE_PROPERTY_CHANGE, {
            entity: this,
            property: prop,
            value: value,
            oldValue: oldValue
          });
        }
        if (messages !== void 0) {
          if ((ref3 = this._messageHolder) != null) {
            ref3.clear(prop);
          }
          this.addMessage(prop, messages);
          if (value != null) {
            ref4 = property._validators;
            for (q = 0, len3 = ref4.length; q < len3; q++) {
              validator = ref4[q];
              if (!validator._disabled && validator instanceof cola.AsyncValidator && validator.get("async")) {
                validator.validate(value, (function(_this) {
                  return function(message) {
                    if (message) {
                      _this.addMessage(prop, message);
                    }
                  };
                })(this));
              }
            }
          }
        } else {
          this.validate(prop);
        }
      }
      return value;
    };

    Entity.prototype.remove = function(detach) {
      if (this.parent) {
        if (this.parent instanceof _EntityList) {
          this.parent.remove(this, detach);
        } else {
          this.setState(_Entity.STATE_DELETED);
          this.parent.set(this._parentProperty, null);
        }
      } else {
        this.setState(_Entity.STATE_DELETED);
      }
      return this;
    };

    Entity.prototype.createChild = function(prop, data) {
      var entityList, property, propertyDataType, provider, ref;
      if (data && data instanceof Array) {
        throw new cola.Exception("Unmatched DataType. expect \"Object\" but \"Array\".");
      }
      property = (ref = this.dataType) != null ? ref.getProperty(prop) : void 0;
      propertyDataType = property != null ? property._dataType : void 0;
      if (propertyDataType && !(propertyDataType instanceof cola.EntityDataType)) {
        throw new cola.Exception("Unmatched DataType. expect \"cola.EntityDataType\" but \"" + propertyDataType._name + "\".");
      }
      if (property != null ? property._aggregated : void 0) {
        entityList = this._get(prop, "never");
        if (entityList == null) {
          entityList = new cola.EntityList(null, propertyDataType);
          provider = property._provider;
          if (provider) {
            entityList.pageSize = provider._pageSize;
            entityList._providerInvoker = provider.getInvoker({
              data: this
            });
          }
          this._disableWriteObservers++;
          this._set(prop, entityList);
          this._disableWriteObservers--;
        }
        return entityList.insert(data);
      } else {
        return this._set(prop, data);
      }
    };

    Entity.prototype.createBrother = function(data) {
      var brother, parent;
      if (data && data instanceof Array) {
        throw new cola.Exception("Unmatched DataType. expect \"Object\" but \"Array\".");
      }
      brother = new _Entity(data, this.dataType);
      brother.setState(_Entity.STATE_NEW);
      parent = this.parent;
      if (parent && parent instanceof _EntityList) {
        parent.insert(brother);
      }
      return brother;
    };

    Entity.prototype.setState = function(state) {
      var oldState;
      if (this.state === state) {
        return this;
      }
      if (this.state === _Entity.STATE_NONE && state === _Entity.STATE_MODIFIED) {
        this._storeOldData();
      }
      oldState = this.state;
      this.state = state;
      this._notify(cola.constants.MESSAGE_EDITING_STATE_CHANGE, {
        entity: this,
        oldState: oldState,
        state: state
      });
      return this;
    };

    Entity.prototype._storeOldData = function() {
      var data, oldData, p;
      if (this._oldData) {
        return;
      }
      data = this._data;
      oldData = this._oldData = {};
      for (p in data) {
        value = data[p];
        if (value && (value instanceof _Entity || value instanceof _EntityList)) {
          continue;
        }
        oldData[p] = value;
      }
    };

    Entity.prototype.getOldValue = function(prop) {
      var ref;
      return (ref = this._oldData) != null ? ref[prop] : void 0;
    };

    Entity.prototype.reset = function(prop) {
      var data;
      if (prop) {
        this._set(prop, void 0);
        this.clearMessages(prop);
      } else {
        this.disableObservers();
        data = this._data;
        for (prop in data) {
          value = data[prop];
          if (value !== void 0) {
            delete data[prop];
          }
        }
        this.resetState();
        this.enableObservers();
        this._notify(cola.constants.MESSAGE_REFRESH, {
          data: this
        });
      }
      return this;
    };

    Entity.prototype.resetState = function() {
      delete this._oldData;
      this.clearMessages();
      this.setState(_Entity.STATE_NONE);
      return this;
    };

    Entity.prototype.getDataType = function(path) {
      var data, dataType, l, len1, part, parts, property;
      if (path) {
        dataType = this.dataType;
        if (dataType) {
          parts = path.split(".");
          for (l = 0, len1 = parts.length; l < len1; l++) {
            part = parts[l];
            property = typeof dataType.getProperty === "function" ? dataType.getProperty(part) : void 0;
            if (property == null) {
              break;
            }
            dataType = property.get("dataType");
            if (dataType == null) {
              break;
            }
          }
        }
      } else {
        dataType = this.dataType;
      }
      if (dataType == null) {
        data = this.get(path);
        dataType = data != null ? data.dataType : void 0;
      }
      return dataType;
    };

    Entity.prototype.getPath = _getEntityPath;

    Entity.prototype.flush = function(property, loadMode) {
      var callback, oldLoadMode, propertyDef, provider;
      if (loadMode == null) {
        loadMode = "async";
      }
      propertyDef = this.dataType.getProperty(property);
      provider = propertyDef != null ? propertyDef._provider : void 0;
      if (!provider) {
        throw new cola.Exception("Provider undefined.");
      }
      this._set(property, void 0);
      if (loadMode && (typeof loadMode === "function" || typeof loadMode === "object")) {
        callback = loadMode;
        loadMode = "async";
      }
      oldLoadMode = provider._loadMode;
      provider._loadMode = "lazy";
      try {
        return this._get(property, loadMode, {
          complete: (function(_this) {
            return function(success, result) {
              cola.callback(callback, success, result);
            };
          })(this)
        });
      } finally {
        provider._loadMode = oldLoadMode;
      }
    };

    Entity.prototype._setDataModel = function(dataModel) {
      var data, p;
      if (this._dataModel === dataModel) {
        return;
      }
      if (this._dataModel) {
        this._dataModel.onEntityDetach(this);
      }
      this._dataModel = dataModel;
      if (dataModel) {
        dataModel.onEntityAttach(this);
      }
      if (this._mayHasSubEntity) {
        data = this._data;
        for (p in data) {
          value = data[p];
          if (value && (value instanceof _Entity || value instanceof _EntityList)) {
            value._setDataModel(dataModel);
          }
        }
      }
    };

    Entity.prototype.watch = _watch;

    Entity.prototype.unwatch = _unwatch;

    Entity.prototype._triggerWatcher = _triggerWatcher;

    Entity.prototype._onPathChange = function() {
      var data, p;
      delete this._pathCache;
      if (this._mayHasSubEntity) {
        data = this._data;
        for (p in data) {
          value = data[p];
          if (value && (value instanceof _Entity || value instanceof _EntityList)) {
            value._onPathChange();
          }
        }
      }
    };

    Entity.prototype.disableObservers = function() {
      if (this._disableObserverCount < 0) {
        this._disableObserverCount = 1;
      } else {
        this._disableObserverCount++;
      }
      return this;
    };

    Entity.prototype.enableObservers = function() {
      if (this._disableObserverCount < 1) {
        this._disableObserverCount = 0;
      } else {
        this._disableObserverCount--;
      }
      if (this._disableObserverCount < 1) {
        this._disableObserverCount = 0;
      } else {
        this._disableObserverCount--;
      }
      return this;
    };

    Entity.prototype.notifyObservers = function() {
      this._notify(cola.constants.MESSAGE_REFRESH, {
        data: this
      });
      return this;
    };

    Entity.prototype._notify = function(type, arg) {
      var path;
      if (this._disableObserverCount === 0) {
        delete arg.timestamp;
        path = this.getPath(true);
        if ((type === cola.constants.MESSAGE_PROPERTY_CHANGE || type === cola.constants.MESSAGE_VALIDATION_STATE_CHANGE || type === cola.constants.MESSAGE_LOADING_START || type === cola.constants.MESSAGE_LOADING_END) && arg.property) {
          if (path) {
            path = path.concat(arg.property);
          } else {
            path = [arg.property];
          }
        }
        this._doNotify(path, type, arg);
        if (type === cola.constants.MESSAGE_PROPERTY_CHANGE || type === cola.constants.MESSAGE_REFRESH) {
          this._triggerWatcher([arg.property || "*"], type, arg);
        }
      }
    };

    Entity.prototype._doNotify = function(path, type, arg) {
      var ref;
      if ((ref = this._dataModel) != null) {
        ref._onDataMessage(path, type, arg);
      }
    };

    Entity.prototype._validate = function(prop) {
      var data, l, len1, message, messageChanged, property, ref, validator;
      property = this.dataType.getProperty(prop);
      if (property) {
        if (property._validators) {
          data = this._data[prop];
          if (data && (data instanceof cola.Provider || data instanceof cola.AjaxServiceInvoker)) {
            return;
          }
          ref = property._validators;
          for (l = 0, len1 = ref.length; l < len1; l++) {
            validator = ref[l];
            if (!validator._disabled) {
              if (validator instanceof cola.AsyncValidator && validator.get("async")) {
                validator.validate(data, (function(_this) {
                  return function(message) {
                    if (message) {
                      _this.addMessage(prop, message);
                    }
                  };
                })(this));
              } else {
                message = validator.validate(data);
                if (message) {
                  this._addMessage(prop, message);
                  messageChanged = true;
                }
              }
            }
          }
        }
      }
      return messageChanged;
    };

    Entity.prototype.validate = function(prop) {
      var keyMessage, l, len1, oldKeyMessage, property, ref, ref1;
      if (this._messageHolder) {
        oldKeyMessage = this._messageHolder.getKeyMessage();
        this._messageHolder.clear(prop);
      }
      if (this.dataType) {
        if (prop) {
          this._validate(prop);
          this._notify(cola.constants.MESSAGE_VALIDATION_STATE_CHANGE, {
            entity: this,
            property: prop
          });
        } else {
          ref = this.dataType.getProperties().elements;
          for (l = 0, len1 = ref.length; l < len1; l++) {
            property = ref[l];
            this._validate(property._property);
            this._notify(cola.constants.MESSAGE_VALIDATION_STATE_CHANGE, {
              entity: this,
              property: property._property
            });
          }
        }
      }
      keyMessage = (ref1 = this._messageHolder) != null ? ref1.getKeyMessage() : void 0;
      if ((oldKeyMessage || keyMessage) && oldKeyMessage !== keyMessage) {
        this._notify(cola.constants.MESSAGE_VALIDATION_STATE_CHANGE, {
          entity: this
        });
      }
      return !((keyMessage != null ? keyMessage.type : void 0) === "error");
    };

    Entity.prototype._addMessage = function(prop, message) {
      var l, len1, m, messageHolder, topKeyChanged;
      messageHolder = this._messageHolder;
      if (!messageHolder) {
        this._messageHolder = messageHolder = new _Entity.MessageHolder();
      }
      if (message instanceof Array) {
        for (l = 0, len1 = message.length; l < len1; l++) {
          m = message[l];
          if (messageHolder.add(prop, m)) {
            topKeyChanged = true;
          }
        }
      } else {
        if (messageHolder.add(prop, message)) {
          topKeyChanged = true;
        }
      }
      return topKeyChanged;
    };

    Entity.prototype.addMessage = function(prop, message) {
      var topKeyChanged;
      if (arguments.length === 1) {
        message = prop;
        prop = "$";
      }
      if (prop === "$") {
        this._notify(cola.constants.MESSAGE_VALIDATION_STATE_CHANGE, {
          entity: this
        });
      } else {
        topKeyChanged = this._addMessage(prop, message);
        this._notify(cola.constants.MESSAGE_VALIDATION_STATE_CHANGE, {
          entity: this,
          property: prop
        });
        if (topKeyChanged) {
          this._notify(cola.constants.MESSAGE_VALIDATION_STATE_CHANGE, {
            entity: this
          });
        }
      }
      return this;
    };

    Entity.prototype.getKeyMessage = function(prop) {
      var ref;
      return (ref = this._messageHolder) != null ? ref.getKeyMessage(prop) : void 0;
    };

    Entity.prototype.getMessages = function(prop) {
      var ref;
      return (ref = this._messageHolder) != null ? ref.getMessages(prop) : void 0;
    };

    Entity.prototype.clearMessages = function(prop) {
      var hasPropMessage, topKeyChanged;
      if (!this._messageHolder) {
        return this;
      }
      if (prop) {
        hasPropMessage = this._messageHolder.getKeyMessage(prop);
      }
      topKeyChanged = this._messageHolder.clear(prop);
      if (hasPropMessage) {
        this._notify(cola.constants.MESSAGE_VALIDATION_STATE_CHANGE, {
          entity: this,
          property: prop
        });
      }
      if (topKeyChanged) {
        this._notify(cola.constants.MESSAGE_VALIDATION_STATE_CHANGE, {
          entity: this
        });
      }
      return this;
    };

    Entity.prototype.findMessages = function(prop, type) {
      var ref;
      return (ref = this._messageHolder) != null ? ref.findMessages(prop, type) : void 0;
    };

    Entity.prototype.toJSON = function(options) {
      var data, json, oldData, prop, simpleValue, state;
      state = (options != null ? options.state : void 0) || false;
      oldData = (options != null ? options.oldData : void 0) || false;
      simpleValue = (options != null ? options.simpleValue : void 0) || false;
      data = this._data;
      json = {};
      for (prop in data) {
        value = data[prop];
        if (prop.charCodeAt(0) === 36) {
          continue;
        }
        if (value) {
          if (value instanceof cola.AjaxServiceInvoker) {
            continue;
          } else if (value instanceof _Entity || value instanceof _EntityList) {
            if (simpleValue) {
              continue;
            }
            value = value.toJSON(options);
          }
        }
        json[prop] = value;
      }
      if (state) {
        json.$state = this.state;
      }
      if (oldData && this._oldData) {
        json.$oldData = this._oldData;
      }
      return json;
    };

    return Entity;

  })();

  LinkedList = (function() {
    function LinkedList() {}

    LinkedList.prototype._size = 0;

    LinkedList.prototype._insertElement = function(element, insertMode, refEntity) {
      var next, previous;
      if (!this._first) {
        this._first = this._last = element;
      } else {
        if (!insertMode || insertMode === "end") {
          element._previous = this._last;
          delete element._next;
          this._last._next = element;
          this._last = element;
        } else if (insertMode === "before") {
          previous = refEntity._previous;
          if (previous != null) {
            previous._next = element;
          }
          refEntity._previous = element;
          element._previous = previous;
          element._next = refEntity;
          if (this._first === refEntity) {
            this._first = element;
          }
        } else if (insertMode === "after") {
          next = refEntity._next;
          if (next != null) {
            next._previous = element;
          }
          refEntity._next = element;
          element._previous = refEntity;
          element._next = next;
          if (this._last === refEntity) {
            this._last = element;
          }
        } else if (insertMode === "begin") {
          delete element._previous;
          element._next = this._first;
          this._first._previous = element;
          this._first = element;
        }
      }
      element._page = this;
      this._size++;
    };

    LinkedList.prototype._removeElement = function(element) {
      var next, previous;
      previous = element._previous;
      next = element._next;
      if (previous != null) {
        previous._next = next;
      }
      if (next != null) {
        next._previous = previous;
      }
      if (this._first === element) {
        this._first = next;
      }
      if (this._last === element) {
        this._last = previous;
      }
      this._size++;
    };

    LinkedList.prototype._clearElements = function() {
      this._first = this._last = null;
      this._size = 0;
    };

    return LinkedList;

  })();

  Page = (function(superClass) {
    extend(Page, superClass);

    Page.prototype.loaded = false;

    Page.prototype.entityCount = 0;

    function Page(entityList1, pageNo1) {
      this.entityList = entityList1;
      this.pageNo = pageNo1;
    }

    Page.prototype.initData = function(json) {
      var data, dataType, entity, entityList, l, len1, rawJson;
      rawJson = json;
      entityList = this.entityList;
      if (json.hasOwnProperty("$data")) {
        json = rawJson.$data;
      }
      if (!(json instanceof Array)) {
        throw new cola.Exception("Unmatched DataType. expect \"Array\" but \"Object\".");
      }
      dataType = entityList.dataType;
      for (l = 0, len1 = json.length; l < len1; l++) {
        data = json[l];
        entity = new _Entity(data, dataType);
        this._insertElement(entity);
      }
      if (rawJson.$entityCount != null) {
        entityList.totalEntityCount = rawJson.$entityCount;
      }
      if (entityList.totalEntityCount != null) {
        if (entityList.pageSize) {
          entityList.pageCount = parseInt((entityList.totalEntityCount + entityList.pageSize - 1) / entityList.pageSize);
        }
        entityList.pageCountDetermined = true;
      }
      entityList.entityCount += json.length;
      entityList.timestamp = cola.sequenceNo();
      entityList._notify(cola.constants.MESSAGE_REFRESH, {
        data: entityList
      });
    };

    Page.prototype._insertElement = function(entity, insertMode, refEntity) {
      var entityList;
      Page.__super__._insertElement.call(this, entity, insertMode, refEntity);
      entityList = this.entityList;
      entity._page = this;
      entity.parent = entityList;
      delete entity._parentProperty;
      if (!this.dontAutoSetCurrent && (entityList.current == null)) {
        if (entity.state !== _Entity.STATE_DELETED) {
          entityList.current = entity;
          entityList._setCurrentPage(entity._page);
        }
      }
      entity._setDataModel(entityList._dataModel);
      entity._onPathChange();
      if (entity.state !== _Entity.STATE_DELETED) {
        this.entityCount++;
      }
    };

    Page.prototype._removeElement = function(entity) {
      Page.__super__._removeElement.call(this, entity);
      delete entity._page;
      delete entity.parent;
      entity._setDataModel(null);
      entity._onPathChange();
      if (entity.state !== _Entity.STATE_DELETED) {
        this.entityCount--;
      }
    };

    Page.prototype._clearElements = function() {
      var entity;
      entity = this._first;
      while (entity) {
        delete entity._page;
        delete entity.parent;
        entity._setDataModel(null);
        entity._onPathChange();
        entity = entity._next;
      }
      this.entityCount = 0;
      Page.__super__._clearElements.call(this);
    };

    Page.prototype.loadData = function(callback) {
      var providerInvoker, result;
      providerInvoker = this.entityList._providerInvoker;
      if (providerInvoker) {
        providerInvoker.pageSize = this.entityList.pageSize;
        providerInvoker.pageNo = this.pageNo;
        if (callback) {
          providerInvoker.invokeAsync({
            complete: (function(_this) {
              return function(success, result) {
                if (success) {
                  _this.initData(result);
                }
                return cola.callback(callback, success, result);
              };
            })(this)
          });
        } else {
          result = providerInvoker.invokeSync();
          this.initData(result);
        }
      }
    };

    return Page;

  })(LinkedList);

  cola.EntityList = (function(superClass) {
    extend(EntityList, superClass);

    EntityList.prototype.current = null;

    EntityList.prototype.entityCount = 0;

    EntityList.prototype.pageMode = "append";

    EntityList.prototype.pageSize = 0;

    EntityList.prototype.pageNo = 1;

    EntityList.prototype.pageCount = 1;

    EntityList.prototype._disableObserverCount = 0;

    function EntityList(array, dataType) {
      this.id = cola.uniqueId();
      this.timestamp = cola.sequenceNo();
      this.dataType = dataType;
      if (array) {
        this.fillData(array);
      }
    }

    EntityList.prototype.fillData = function(array) {
      var page;
      page = this._findPage(this.pageNo);
      if (page == null) {
        page = new Page(this, this.pageNo);
      }
      this._insertElement(page, "begin");
      page.initData(array);
    };

    EntityList.prototype._setDataModel = function(dataModel) {
      var next, page;
      if (this._dataModel === dataModel) {
        return;
      }
      this._dataModel = dataModel;
      page = this._first;
      if (!page) {
        return;
      }
      next = page._first;
      while (page) {
        if (next) {
          next._setDataModel(dataModel);
          next = next._next;
        } else {
          page = page._next;
          next = page != null ? page._first : void 0;
        }
      }
    };

    EntityList.prototype.watch = _watch;

    EntityList.prototype.unwatch = _unwatch;

    EntityList.prototype._triggerWatcher = _triggerWatcher;

    EntityList.prototype._setCurrentPage = function(page) {
      this._currentPage = page;
      this.pageNo = (page != null ? page.pageNo : void 0) || 1;
      this.timestamp = cola.sequenceNo();
    };

    EntityList.prototype._onPathChange = function() {
      var next, page;
      delete this._pathCache;
      page = this._first;
      if (!page) {
        return;
      }
      next = page._first;
      while (page) {
        if (next) {
          next._onPathChange();
          next = next._next;
        } else {
          page = page._next;
          next = page != null ? page._first : void 0;
        }
      }
    };

    EntityList.prototype._findPrevious = function(entity) {
      var page, previous;
      if (entity && entity.parent !== this) {
        return;
      }
      if (entity) {
        page = entity._page;
        previous = entity._previous;
      } else {
        page = this._last;
        previous = page._last;
      }
      while (page) {
        if (previous) {
          if (previous.state !== _Entity.STATE_DELETED) {
            return previous;
          } else {
            previous = previous._previous;
          }
        } else {
          page = page._previous;
          previous = page != null ? page._last : void 0;
        }
      }
    };

    EntityList.prototype._findNext = function(entity) {
      var next, page;
      if (entity && entity.parent !== this) {
        return;
      }
      if (entity) {
        page = entity._page;
        next = entity._next;
      } else {
        page = this._first;
        next = page._first;
      }
      while (page) {
        if (next) {
          if (next.state !== _Entity.STATE_DELETED) {
            return next;
          } else {
            next = next._next;
          }
        } else {
          page = page._next;
          next = page != null ? page._first : void 0;
        }
      }
    };

    EntityList.prototype._findPage = function(pageNo) {
      var page;
      if (pageNo < 1) {
        return null;
      }
      if (pageNo > this.pageCount) {
        if (this.pageCountDetermined || pageNo > (this.pageCount + 1)) {
          return null;
        }
      }
      page = this._currentPage || this._first;
      if (!page) {
        return null;
      }
      if (page.pageNo === pageNo) {
        return page;
      } else if (page.pageNo < pageNo) {
        page = page._next;
        while (page != null) {
          if (page.pageNo === pageNo) {
            return page;
          } else if (page.pageNo > pageNo) {
            break;
          }
          page = page._next;
        }
      } else {
        page = page._previous;
        while (page != null) {
          if (page.pageNo === pageNo) {
            return page;
          } else if (page.pageNo < pageNo) {
            break;
          }
          page = page._previous;
        }
      }
      return null;
    };

    EntityList.prototype._createPage = function(pageNo) {
      var insertMode, page, refPage;
      if (pageNo < 1) {
        return null;
      }
      if (pageNo > this.pageCount) {
        if (this.pageCountDetermined || pageNo > (this.pageCount + 1)) {
          return null;
        }
      }
      insertMode = "end";
      refPage = this._currentPage || this._first;
      if (refPage) {
        if (refPage.page === pageNo - 1) {
          insertMode = "after";
        } else if (refPage.page === pageNo + 1) {
          insertMode = "before";
        } else {
          page = this._last;
          while (page) {
            if (page.pageNo < pageNo) {
              refPage = page;
              insertMode = "after";
              break;
            }
            page = page._previous;
          }
        }
      }
      page = new Page(this, pageNo);
      this._insertElement(page, insertMode, refPage);
      return page;
    };

    EntityList.prototype.hasNextPage = function() {
      var pageNo;
      pageNo = this.pageNo + 1;
      return !this.pageCountDetermined || pageNo <= this.pageCount;
    };

    EntityList.prototype._loadPage = function(pageNo, setCurrent, loadMode) {
      var callback, entity, page;
      if (loadMode == null) {
        loadMode = "async";
      }
      if (loadMode && (typeof loadMode === "function" || typeof loadMode === "object")) {
        callback = loadMode;
        loadMode = "async";
      }
      page = this._findPage(pageNo);
      if (page !== this._currentPage) {
        if (page) {
          this._setCurrentPage(page);
          if (setCurrent) {
            entity = page._first;
            while (entity) {
              if (entity.state !== _Entity.STATE_DELETED) {
                this.setCurrent(entity);
                break;
              }
              entity = entity._next;
            }
          }
          cola.callback(callback, true);
        } else if (loadMode !== "never") {
          if (setCurrent) {
            this.setCurrent(null);
          }
          page = this._createPage(pageNo);
          if (page) {
            if (loadMode === "async") {
              page.loadData({
                complete: (function(_this) {
                  return function(success, result) {
                    if (success) {
                      _this._setCurrentPage(page);
                      if (page.entityCount && _this.pageCount < pageNo) {
                        _this.pageCount = pageNo;
                      }
                    }
                    cola.callback(callback, success, result);
                  };
                })(this)
              });
            } else {
              page.loadData();
              this._setCurrentPage(page);
              cola.callback(callback, true);
            }
          }
        }
      }
      return this;
    };

    EntityList.prototype.loadPage = function(pageNo, loadMode) {
      return this._loadPage(pageNo, false, loadMode);
    };

    EntityList.prototype.gotoPage = function(pageNo, loadMode) {
      if (pageNo < 1) {
        pageNo = 1;
      } else if (this.pageCountDetermined && pageNo > this.pageCount) {
        pageNo = this.pageCount;
      }
      return this._loadPage(pageNo, true, loadMode);
    };

    EntityList.prototype.firstPage = function(loadMode) {
      this.gotoPage(1, loadMode);
      return this;
    };

    EntityList.prototype.previousPage = function(loadMode) {
      var pageNo;
      pageNo = this.pageNo - 1;
      if (pageNo < 1) {
        pageNo = 1;
      }
      this.gotoPage(pageNo, loadMode);
      return this;
    };

    EntityList.prototype.nextPage = function(loadMode) {
      var pageNo;
      pageNo = this.pageNo + 1;
      if (this.pageCountDetermined && pageNo > this.pageCount) {
        pageNo = this.pageCount;
      }
      this.gotoPage(pageNo, loadMode);
      return this;
    };

    EntityList.prototype.lastPage = function(loadMode) {
      this.gotoPage(this.pageCount, loadMode);
      return this;
    };

    EntityList.prototype.insert = function(entity, insertMode, refEntity) {
      var page;
      if (insertMode === "before" || insertMode === "after") {
        if (refEntity && refEntity.parent !== this) {
          refEntity = null;
        }
        if (refEntity == null) {
          refEntity = this.current;
        }
        if (refEntity) {
          page = refEntity._page;
        }
      } else if (this.pageMode === "append") {
        if (insertMode === "end") {
          page = this._last;
        } else if (insertMode === "begin") {
          page = this._first;
        }
      }
      if (!page) {
        page = this._currentPage;
        if (!page) {
          this.gotoPage(1);
          page = this._currentPage;
        }
      }
      if (entity instanceof _Entity) {
        if (entity.parent && entity.parent !== this) {
          throw new cola.Exception("Entity is already belongs to another owner. \"" + (this._parentProperty || "Unknown") + "\".");
        }
        if (entity.state === _Entity.STATE_DELETED) {
          entity.setState(_Entity.STATE_NONE);
        }
      } else {
        entity = new _Entity(entity, this.dataType);
        entity.setState(_Entity.STATE_NEW);
      }
      page.dontAutoSetCurrent = true;
      page._insertElement(entity, insertMode, refEntity);
      page.dontAutoSetCurrent = false;
      if (entity.state !== _Entity.STATE_DELETED) {
        this.entityCount++;
      }
      this.timestamp = cola.sequenceNo();
      this._notify(cola.constants.MESSAGE_INSERT, {
        entityList: this,
        entity: entity,
        insertMode: insertMode,
        refEntity: refEntity
      });
      if (!this.current) {
        this.setCurrent(entity);
      }
      return entity;
    };

    EntityList.prototype.remove = function(entity, detach) {
      var changeCurrent, newCurrent, page;
      if (entity == null) {
        entity = this.current;
        if (entity == null) {
          return void 0;
        }
      }
      if (entity.parent !== this) {
        return void 0;
      }
      if (entity === this.current) {
        changeCurrent = true;
        newCurrent = this._findNext(entity);
        if (!newCurrent) {
          newCurrent = this._findPrevious(entity);
        }
      }
      page = entity._page;
      if (detach) {
        page._removeElement(entity);
        this.entityCount--;
      } else if (entity.state === _Entity.STATE_NEW) {
        entity.setState(_Entity.STATE_DELETED);
        page._removeElement(entity);
        this.entityCount--;
      } else if (entity.state !== _Entity.STATE_DELETED) {
        entity.setState(_Entity.STATE_DELETED);
        this.entityCount--;
      }
      this.timestamp = cola.sequenceNo();
      this._notify(cola.constants.MESSAGE_REMOVE, {
        entityList: this,
        entity: entity
      });
      if (changeCurrent) {
        this.setCurrent(newCurrent);
      }
      return entity;
    };

    EntityList.prototype.setCurrent = function(entity) {
      var oldCurrent;
      if (this.current === entity || (entity != null ? entity.state : void 0) === cola.Entity.STATE_DELETED) {
        return this;
      }
      if (entity && entity.parent !== this) {
        throw new cola.Exception("The entity is not belongs to this EntityList.");
      }
      oldCurrent = this.current;
      if (oldCurrent) {
        oldCurrent._onPathChange();
      }
      this.current = entity;
      if (entity) {
        this._setCurrentPage(entity._page);
        entity._onPathChange();
      }
      this._notify(cola.constants.MESSAGE_CURRENT_CHANGE, {
        entityList: this,
        current: entity,
        oldCurrent: oldCurrent
      });
      return this;
    };

    EntityList.prototype.first = function() {
      var entity;
      entity = this._findNext();
      if (entity) {
        this.setCurrent(entity);
        return entity;
      } else {
        return this.current;
      }
    };

    EntityList.prototype.previous = function() {
      var entity;
      entity = this._findPrevious(this.current);
      if (entity) {
        this.setCurrent(entity);
        return entity;
      } else {
        return this.current;
      }
    };

    EntityList.prototype.next = function() {
      var entity;
      entity = this._findNext(this.current);
      if (entity) {
        this.setCurrent(entity);
        return entity;
      } else {
        return this.current;
      }
    };

    EntityList.prototype.last = function() {
      var entity;
      entity = this._findPrevious();
      if (entity) {
        this.setCurrent(entity);
        return entity;
      } else {
        return this.current;
      }
    };

    EntityList.prototype._reset = function() {
      var page;
      this.current = null;
      this.entityCount = 0;
      this.pageNo = 1;
      this.pageCount = 1;
      page = this._first;
      while (page) {
        page._clearElements();
        page = page._next;
      }
      delete this._currentPage;
      delete this._first;
      delete this._last;
      this.timestamp = cola.sequenceNo();
      return this;
    };

    EntityList.prototype.disableObservers = function() {
      if (this._disableObserverCount < 0) {
        this._disableObserverCount = 1;
      } else {
        this._disableObserverCount++;
      }
      return this;
    };

    EntityList.prototype.enableObservers = function() {
      if (this._disableObserverCount < 1) {
        this._disableObserverCount = 0;
      } else {
        this._disableObserverCount--;
      }
      return this;
    };

    EntityList.prototype.notifyObservers = function() {
      this._notify(cola.constants.MESSAGE_REFRESH, {
        data: this
      });
      return this;
    };

    EntityList.prototype._notify = function(type, arg) {
      var ref;
      if (this._disableObserverCount === 0) {
        if ((ref = this._dataModel) != null) {
          ref._onDataMessage(this.getPath(true), type, arg);
        }
        if (type === cola.constants.MESSAGE_CURRENT_CHANGE || type === cola.constants.MESSAGE_INSERT || type === cola.constants.MESSAGE_REMOVE) {
          this._triggerWatcher(["*"], type, arg);
        }
      }
    };

    EntityList.prototype.each = function(fn, options) {
      var deleted, i, next, page, pageNo;
      page = this._first;
      if (!page) {
        return this;
      }
      if (options != null) {
        if (typeof options === "boolean") {
          deleted = options;
        } else {
          deleted = options.deleted;
          pageNo = options.pageNo;
          if (!pageNo && options.currentPage) {
            pageNo = this.pageNo;
          }
        }
      }
      if (pageNo > 1) {
        page = this._findPage(pageNo);
        if (!page) {
          return this;
        }
      }
      next = page._first;
      i = 0;
      while (page) {
        if (next) {
          if (deleted || next.state !== _Entity.STATE_DELETED) {
            if (fn.call(this, next, i++) === false) {
              break;
            }
          }
          next = next._next;
        } else if (page && !pageNo) {
          page = page._next;
          next = page != null ? page._first : void 0;
        } else {
          break;
        }
      }
      return this;
    };

    EntityList.prototype.getPath = _getEntityPath;

    EntityList.prototype.toJSON = function(options) {
      var array, deleted, next, page;
      deleted = options != null ? options.deleted : void 0;
      array = [];
      page = this._first;
      if (page) {
        next = page._first;
        while (page) {
          if (next) {
            if (deleted || next.state !== _Entity.STATE_DELETED) {
              array.push(next.toJSON(options));
            }
            next = next._next;
          } else {
            page = page._next;
            next = page != null ? page._first : void 0;
          }
        }
      }
      return array;
    };

    EntityList.prototype.toArray = function() {
      var array, next, page;
      array = [];
      page = this._first;
      if (page) {
        next = page._first;
        while (page) {
          if (next) {
            if (next.state !== _Entity.STATE_DELETED) {
              array.push(next);
            }
            next = next._next;
          } else {
            page = page._next;
            next = page != null ? page._first : void 0;
          }
        }
      }
      return array;
    };

    EntityList.prototype.filter = function(criteria, option) {
      criteria = cola._trimCriteria(criteria, option);
      return _filterCollection(this, criteria, option);
    };

    EntityList.prototype.where = function(criteria, option) {
      if (option == null) {
        option = {};
      }
      if (option.caseSensitive === void 0) {
        option.caseSensitive = true;
      }
      if (option.strict === void 0) {
        option.strict = true;
      }
      criteria = cola._trimCriteria(criteria, option);
      return _filterCollection(this, criteria, option);
    };

    EntityList.prototype.find = function(criteria, option) {
      var result;
      option.one = true;
      result = cola.util.where(this, criteria, option);
      return result != null ? result[0] : void 0;
    };

    return EntityList;

  })(LinkedList);

  _Entity = cola.Entity;

  _EntityList = cola.EntityList;

  _Entity._evalDataPath = _evalDataPath = function(data, path, noEntityList, loadMode, callback, context) {
    var i, isLast, l, lastIndex, len1, part, parts, returnCurrent;
    if (path) {
      parts = path.split(".");
      lastIndex = parts.length - 1;
      for (i = l = 0, len1 = parts.length; l < len1; i = ++l) {
        part = parts[i];
        returnCurrent = false;
        if (i === 0 && data instanceof _EntityList) {
          if (part === "#") {
            data = data.current;
          } else {
            data = data[part];
          }
        } else {
          isLast = i === lastIndex;
          if (!noEntityList) {
            if (!isLast) {
              returnCurrent = true;
            }
            if (part.charCodeAt(part.length - 1) === 35) {
              returnCurrent = true;
              part = part.substring(0, part.length - 1);
            }
          }
          if (data instanceof _Entity) {
            if (typeof data._get === "function") {
              data = data._get(part, loadMode, callback, context);
            } else {

            }
            if (data && data instanceof _EntityList) {
              if (noEntityList || returnCurrent) {
                data = data.current;
              }
            }
          } else {
            data = data[part];
          }
        }
        if (data == null) {
          break;
        }
      }
    }
    return data;
  };

  _Entity._setValue = _setValue = function(entity, path, value, context) {
    var i, part1, part2;
    i = path.lastIndexOf(".");
    if (i > 0) {
      part1 = path.substring(0, i);
      part2 = path.substring(i + 1);
      entity = _evalDataPath(entity, part1, true, "never", context);
      if ((entity != null) && !(entity instanceof _EntityList)) {
        if (entity instanceof cola.AjaxServiceInvoker) {
          entity = void 0;
        } else if (typeof entity._set === "function") {
          entity._set(part2, value);
        } else {
          entity[part2] = value;
        }
      } else {
        throw new cola.Exception("Cannot set value to EntityList \"" + path + "\".");
      }
    } else if (typeof entity._set === "function") {
      entity._set(path, value);
    } else {
      entity[path] = value;
    }
  };

  _Entity._getEntityId = function(entity) {
    if (!entity) {
      return null;
    }
    if (entity instanceof cola.Entity) {
      return entity.id;
    } else if (typeof entity === "object") {
      if (entity._id == null) {
        entity._id = cola.uniqueId();
      }
      return entity._id;
    }
  };

  TYPE_SEVERITY = {
    VALIDATION_INFO: 1,
    VALIDATION_WARN: 2,
    VALIDATION_ERROR: 4
  };

  cola.Entity.MessageHolder = (function() {
    function MessageHolder() {
      this.keyMessage = {};
      this.propertyMessages = {};
    }

    MessageHolder.prototype.compare = function(message1, message2) {
      return (TYPE_SEVERITY[message1.type] || 0) - (TYPE_SEVERITY[message2.type] || 0);
    };

    MessageHolder.prototype.add = function(prop, message) {
      var isTopKey, keyMessage, messages, topKeyChanged;
      messages = this.propertyMessages[prop];
      if (!messages) {
        this.propertyMessages[prop] = [message];
      } else {
        messages.push(message);
      }
      isTopKey = prop === "$";
      if (keyMessage) {
        if (this.compare(message, keyMessage) > 0) {
          this.keyMessage[prop] = message;
          topKeyChanged = isTopKey;
        }
      } else {
        this.keyMessage[prop] = message;
        topKeyChanged = isTopKey;
      }
      if (!topKeyChanged && !isTopKey) {
        keyMessage = this.keyMessage["$"];
        if (keyMessage) {
          if (this.compare(message, keyMessage) > 0) {
            this.keyMessage["$"] = message;
            topKeyChanged = true;
          }
        } else {
          this.keyMessage["$"] = message;
          topKeyChanged = true;
        }
      }
      return topKeyChanged;
    };

    MessageHolder.prototype.clear = function(prop) {
      var keyMessage, l, len1, message, messages, p, ref, topKeyChanged;
      if (prop) {
        delete this.propertyMessages[prop];
        delete this.keyMessage[prop];
        ref = this.propertyMessages;
        for (p in ref) {
          messages = ref[p];
          for (l = 0, len1 = messages.length; l < len1; l++) {
            message = messages[l];
            if (!keyMessage) {
              keyMessage = message;
            } else if (this.compare(message, keyMessage) > 0) {
              keyMessage = message;
            } else {
              continue;
            }
            if (keyMessage.type === "error") {
              break;
            }
          }
        }
        topKeyChanged = this.keyMessage["$"] !== keyMessage;
        if (topKeyChanged) {
          this.keyMessage["$"] = keyMessage;
        }
      } else {
        topKeyChanged = true;
        this.keyMessage = {};
        this.propertyMessages = {};
      }
      return topKeyChanged;
    };

    MessageHolder.prototype.getMessages = function(prop) {
      if (prop == null) {
        prop = "$";
      }
      return this.propertyMessages[prop];
    };

    MessageHolder.prototype.getKeyMessage = function(prop) {
      if (prop == null) {
        prop = "$";
      }
      return this.keyMessage[prop];
    };

    MessageHolder.prototype.findMessages = function(prop, type) {
      var l, len1, len2, m, messages, ms, o, p, ref;
      if (prop) {
        ms = this.propertyMessages[prop];
        if (type) {
          messages = [];
          for (l = 0, len1 = ms.length; l < len1; l++) {
            m = ms[l];
            if (m.type === type) {
              messages.push(m);
            }
          }
        } else {
          messages = ms;
        }
      } else {
        messages = [];
        ref = this.propertyMessages;
        for (p in ref) {
          ms = ref[p];
          for (o = 0, len2 = ms.length; o < len2; o++) {
            m = ms[o];
            if (!type || m.type === type) {
              messages.push(m);
            }
          }
        }
      }
      return messages;
    };

    return MessageHolder;

  })();


  /*
  Functions
   */

  cola.each = function(collection, fn, options) {
    if (collection instanceof cola.EntityList) {
      collection.each(fn, options);
    } else if (collection instanceof Array) {
      if (typeof collection.each === "function") {
        collection.each(fn);
      } else {
        cola.util.each(collection, fn);
      }
    }
  };


  /*
  util
   */

  cola.util.filter = function(data, criteria, option) {
    criteria = cola._trimCriteria(criteria, option);
    return _filterCollection(data, criteria, option);
  };

  cola.util.where = function(data, criteria, option) {
    if (option == null) {
      option = {};
    }
    if (option.caseSensitive === void 0) {
      option.caseSensitive = true;
    }
    if (option.strict === void 0) {
      option.strict = true;
    }
    criteria = cola._trimCriteria(criteria, option);
    return _filterCollection(data, criteria, option);
  };

  cola.util.find = function(data, criteria, option) {
    var result;
    option.one = true;
    result = cola.util.where(data, criteria, option);
    return result != null ? result[0] : void 0;
  };

  cola.util.sort = function(collection, comparator, caseSensitive) {
    return _sortCollection(collection, comparator, caseSensitive);
  };

  cola.util.flush = function(data, loadMode) {
    if (data instanceof cola.Entity || data instanceof cola.EntityList) {
      if (data.parent instanceof cola.Entity && data._parentProperty) {
        data.parent.flush(data._parentProperty, loadMode);
      }
    }
  };


  /*
  index
   */

  EntityIndex = (function() {
    function EntityIndex(data1, property1, option1) {
      var model, ref;
      this.data = data1;
      this.property = property1;
      this.option = option1 != null ? option1 : {};
      this.model = model = (ref = this.data._dataModel) != null ? ref.model : void 0;
      if (!model) {
        throw new cola.Exception("The Entity or EntityList is not belongs to any Model.");
      }
      this.deep = this.option.deep;
      this.isCollection = this.data instanceof cola.EntityList;
      if (!this.deep && !this.isCollection) {
        throw new cola.Exception("Can not build index for single Entity.");
      }
      this.index = {};
      this.idMap = {};
      this.buildIndex();
      model.data.addEntityListener(this);
      return;
    }

    EntityIndex.prototype.buildIndex = function() {
      var data;
      data = this.data;
      if (data instanceof cola.Entity) {
        this._buildIndexForEntity(data);
      } else if (data instanceof cola.EntityList) {
        this._buildIndexForEntityList(data);
      }
    };

    EntityIndex.prototype._buildIndexForEntityList = function(entityList) {
      entityList.each((function(_this) {
        return function(entity) {
          _this._buildIndexForEntity(entity);
        };
      })(this));
    };

    EntityIndex.prototype._buildIndexForEntity = function(entity) {
      var data, p, v;
      value = entity.get(this.property);
      this.index[value + ""] = entity;
      this.idMap[entity.id] = true;
      if (this.deep) {
        data = entity._data;
        for (p in data) {
          v = data[p];
          if (v) {
            if (v instanceof cola.Entity) {
              this._buildIndexForEntity(v);
            } else if (v instanceof cola.EntityList) {
              this._buildIndexForEntityList(v);
            }
          }
        }
      }
    };

    EntityIndex.prototype.onEntityAttach = function(entity) {
      var p, valid;
      if (this.deep) {
        p = entity;
        while (p) {
          if (p === this.data) {
            valid = true;
            break;
          }
          p = p.parent;
        }
      } else if (this.isCollection) {
        valid = entity.parent === this.data;
      } else {
        valid = entity === this.data;
      }
      if (valid) {
        value = entity.get(this.property);
        this.idMap[entity.id] = true;
        this.index[value + ""] = entity;
      }
    };

    EntityIndex.prototype.onEntityDetach = function(entity) {
      if (this.idMap[entity.id]) {
        value = entity.get(this.property);
        delete this.idMap[entity.id];
        delete this.index[value + ""];
      }
    };

    EntityIndex.prototype.find = function(value) {
      return this.index[value + ""];
    };

    EntityIndex.prototype.destroy = function() {
      this.model.data.removeEntityListener(this);
    };

    return EntityIndex;

  })();

  cola.util.buildIndex = function(data, property, option) {
    return new EntityIndex(data, property, option);
  };

  if (typeof exports !== "undefined" && exports !== null) {
    cola = require("./entity");
    if (typeof module !== "undefined" && module !== null) {
      module.exports = cola;
    }
  } else {
    cola = this.cola;
  }


  /*
  Model and Scope
   */

  cola.model = function(name, model) {
    if (arguments.length === 2) {
      if (model) {
        if (cola.model.models[name]) {
          throw new cola.Exception("Duplicated model name \"" + name + "\".");
        }
        cola.model.models[name] = model;
      } else {
        model = cola.removeModel(name);
      }
      return model;
    } else {
      return cola.model.models[name];
    }
  };

  cola.model.models = {};

  cola.removeModel = function(name) {
    var model;
    model = cola.model.models[name];
    delete cola.model.models[name];
    return model;
  };

  cola.Scope = (function() {
    function Scope() {}

    Scope.prototype.get = function(path, loadMode, context) {
      return this.data.get(path, loadMode, context);
    };

    Scope.prototype.set = function(path, data, context) {
      this.data.set(path, data, context);
      return this;
    };

    Scope.prototype.describe = function(property, config) {
      return this.data.describe(property, config);
    };

    Scope.prototype.dataType = function(name) {
      var dataType, l, len1;
      if (typeof name === "string") {
        dataType = this.data.definition(name);
        if (dataType instanceof cola.DataType) {
          return dataType;
        } else {
          return null;
        }
      } else if (name) {
        if (name instanceof Array) {
          for (l = 0, len1 = name.length; l < len1; l++) {
            dataType = name[l];
            if (!(dataType instanceof cola.DataType)) {
              if (dataType.lazy !== false) {
                dataType = new cola.EntityDataType(dataType);
                if (dataType.name) {
                  this.data.regDefinition(dataType.name, dataType);
                }
              }
            }
          }
        } else {
          dataType = name;
          if (!(dataType instanceof cola.DataType)) {
            if (dataType.lazy !== false) {
              dataType = new cola.EntityDataType(dataType);
              if (dataType.name) {
                this.data.regDefinition(dataType.name, dataType);
              }
              return dataType;
            }
          }
        }
      }
    };

    Scope.prototype.definition = function(name) {
      return this.data.definition(name);
    };

    Scope.prototype.flush = function(name, loadMode) {
      this.data.flush(name, loadMode);
      return this;
    };

    Scope.prototype.disableObservers = function() {
      this.data.disableObservers();
      return this;
    };

    Scope.prototype.enableObservers = function() {
      this.data.enableObservers();
      return this;
    };

    Scope.prototype.notifyObservers = function() {
      this.data.notifyObservers();
      return this;
    };

    Scope.prototype.watch = function(path, fn) {
      var l, len1, p, processor;
      processor = {
        _processMessage: function(bindingPath, path, type, arg) {
          fn(path, type, arg);
        }
      };
      if (path instanceof Array) {
        for (l = 0, len1 = path.length; l < len1; l++) {
          p = path[l];
          this.data.bind(p, processor);
        }
      } else {
        this.data.bind(path, processor);
      }
      return this;
    };

    return Scope;

  })();

  cola.Model = (function(superClass) {
    extend(Model, superClass);

    function Model(name, parent) {
      var parentName;
      if (cola.currentScope == null) {
        cola.currentScope = this;
      }
      if (name instanceof cola.Scope) {
        parent = name;
        name = void 0;
      }
      if (name) {
        this.name = name;
        cola.model(name, this);
      }
      if (parent && typeof parent === "string") {
        parentName = parent;
        parent = cola.model(parentName);
      }
      if (parent) {
        this.parent = parent;
      }
      this.data = new cola.DataModel(this);
      if (parent) {
        parent.data.bind("**", this);
      }
      this.action = function(name, action) {
        var a, config, fn, n, scope, store;
        store = this.action;
        if (arguments.length === 1) {
          if (typeof name === "string") {
            scope = this;
            while (store) {
              fn = store[name];
              if (fn) {
                return fn.action || fn;
              }
              scope = scope.parent;
              if (!scope) {
                break;
              }
              store = scope.action;
            }
            return cola.defaultAction[name];
          } else if (name && typeof name === "object") {
            config = name;
            for (n in config) {
              a = config[n];
              this.action(n, a);
            }
          }
          return null;
        } else {
          if (action) {
            store[name] = action;
          } else {
            delete store[name];
          }
          return this;
        }
      };
    }

    Model.prototype.destroy = function() {
      var base;
      if (this.name) {
        cola.removeModel(this.name);
      }
      if (typeof (base = this.data).destroy === "function") {
        base.destroy();
      }
    };

    Model.prototype._processMessage = function(bindingPath, path, type, arg) {
      return this.data._onDataMessage(path, type, arg);
    };

    Model.prototype.$ = function(selector) {
      if (this._$dom == null) {
        this._$dom = $(this._dom);
      }
      return this._$dom.find(selector);
    };

    return Model;

  })(cola.Scope);

  cola.SubScope = (function(superClass) {
    extend(SubScope, superClass);

    function SubScope() {
      return SubScope.__super__.constructor.apply(this, arguments);
    }

    SubScope.prototype.watchPath = function(path) {
      var l, len1, p, parent, paths;
      if (this._watchAllMessages || this._watchPath === path) {
        return;
      }
      this._unwatchPath();
      if (path) {
        this._watchPath = paths = [];
        parent = this.parent;
        if (path instanceof Array) {
          for (l = 0, len1 = path.length; l < len1; l++) {
            p = path[l];
            p = p + ".**";
            paths.push(p);
            if (parent != null) {
              parent.data.bind(p, this);
            }
          }
        } else {
          path = path + ".**";
          paths.push(path);
          if (parent != null) {
            parent.data.bind(path, this);
          }
        }
      } else {
        delete this._watchPath;
      }
    };

    SubScope.prototype._unwatchPath = function() {
      var l, len1, p, parent, path;
      if (!this._watchPath) {
        return;
      }
      path = this._watchPath;
      delete this._watchPath;
      parent = this.parent;
      if (parent) {
        if (path instanceof Array) {
          for (l = 0, len1 = path.length; l < len1; l++) {
            p = path[l];
            parent.data.unbind(p, this);
          }
        } else {
          parent.data.unbind(path, this);
        }
      }
    };

    SubScope.prototype.watchAllMessages = function() {
      var parent;
      if (this._watchAllMessages) {
        return;
      }
      this._watchAllMessages = true;
      this._unwatchPath();
      parent = this.parent;
      if (parent) {
        parent.data.bind("**", this);
        if (typeof parent.watchAllMessages === "function") {
          parent.watchAllMessages();
        }
      }
    };

    SubScope.prototype.destroy = function() {
      if (this.parent) {
        if (this._watchAllMessages) {
          this.parent.data.unbind("**", this);
        } else if (this._watchPath) {
          this._unwatchPath();
        }
      }
      SubScope.__super__.destroy.call(this);
    };

    return SubScope;

  })(cola.Scope);

  cola.AliasScope = (function(superClass) {
    extend(AliasScope, superClass);

    function AliasScope(parent1, expression) {
      var dataType;
      this.parent = parent1;
      if (expression && typeof expression.paths.length === 1 && !expression.hasCallStatement) {
        dataType = this.parent.data.getDataType(expression.paths[0]);
      }
      this.data = new cola.AliasDataModel(this, expression.alias, dataType);
      this.action = this.parent.action;
      this.expression = expression;
      if (!expression.paths && expression.hasCallStatement) {
        this.watchAllMessages();
      } else {
        this.watchPath(expression.paths);
      }
    }

    AliasScope.prototype.destroy = function() {
      AliasScope.__super__.destroy.call(this);
      this.data.destroy();
    };

    AliasScope.prototype.repeatNotification = true;

    AliasScope.prototype._processMessage = function(bindingPath, path, type, arg) {
      if (this.messageTimestamp >= arg.timestamp) {
        return;
      }
      this.data._processMessage(bindingPath, path, type, arg);
    };

    return AliasScope;

  })(cola.SubScope);

  cola.ItemScope = (function(superClass) {
    extend(ItemScope, superClass);

    function ItemScope(parent1, alias) {
      var ref;
      this.parent = parent1;
      this.data = new cola.ItemDataModel(this, alias, (ref = this.parent) != null ? ref.dataType : void 0);
      this.action = this.parent.action;
    }

    ItemScope.prototype.watchPath = function() {};

    ItemScope.prototype.watchAllMessages = function() {
      var ref;
      if ((ref = this.parent) != null) {
        if (typeof ref.watchAllMessages === "function") {
          ref.watchAllMessages();
        }
      }
    };

    ItemScope.prototype._processMessage = function(bindingPath, path, type, arg) {
      return this.data._processMessage(bindingPath, path, type, arg);
    };

    return ItemScope;

  })(cola.SubScope);

  cola.ItemsScope = (function(superClass) {
    extend(ItemsScope, superClass);

    function ItemsScope(parent, expression) {
      this.setParent(parent);
      this.setExpression(expression);
    }

    ItemsScope.prototype.setParent = function(parent) {
      if (this.parent) {
        if (this._watchAllMessages) {
          this.parent.data.unbind("**", this);
        } else if (this._watchPath) {
          this._unwatchPath();
        }
      }
      this.parent = parent;
      this.data = parent.data;
      this.action = parent.action;
      if (this._watchAllMessages) {
        parent.data.bind("**", this);
      } else if (this._watchPath) {
        this.watchPath(this._watchPath);
      }
    };

    ItemsScope.prototype.setExpression = function(expression) {
      var l, len1, path, paths, ref, ref1;
      this.expression = expression;
      if (expression) {
        this.alias = expression.alias;
        paths = [];
        if (expression.paths) {
          ref = expression.paths;
          for (l = 0, len1 = ref.length; l < len1; l++) {
            path = ref[l];
            paths.push(path.split("."));
          }
        }
        this.expressionPath = paths;
        if (!expression.paths && expression.hasCallStatement) {
          this.watchAllMessages();
        } else {
          this.watchPath(expression.paths);
        }
      } else {
        this.alias = "item";
        this.expressionPath = [];
      }
      if (expression && typeof ((ref1 = expression.paths) != null ? ref1.length : void 0) === 1 && !expression.hasCallStatement) {
        this.dataType = this.parent.data.getDataType(expression.paths[0]);
      }
    };

    ItemsScope.prototype.setItems = function() {
      var items, originItems;
      items = arguments[0], originItems = 2 <= arguments.length ? slice.call(arguments, 1) : [];
      this._setItems.apply(this, [items].concat(slice.call(originItems)));
    };

    ItemsScope.prototype.retrieveItems = function(dataCtx) {
      var items;
      if (dataCtx == null) {
        dataCtx = {};
      }
      if (this._retrieveItems) {
        return this._retrieveItems(dataCtx);
      }
      if (this.expression) {
        items = this.expression.evaluate(this.parent, "async", dataCtx);
        this._setItems(items, dataCtx.originData);
      }
    };

    ItemsScope.prototype._setItems = function() {
      var it, items, l, len1, originItems, targetPath;
      items = arguments[0], originItems = 2 <= arguments.length ? slice.call(arguments, 1) : [];
      this.items = items;
      if (originItems && originItems.length === 1) {
        if (originItems[0]) {
          this.originItems = originItems[0];
        }
      } else {
        this.originItems = originItems;
        this.originItems._multiItems = true;
      }
      if (!this.originItems && items instanceof Array) {
        this.originItems = items.$origin;
      }
      targetPath = null;
      if (originItems) {
        for (l = 0, len1 = originItems.length; l < len1; l++) {
          it = originItems[l];
          if (it && it instanceof cola.EntityList) {
            if (targetPath == null) {
              targetPath = [];
            }
            targetPath.push(it.getPath());
          }
        }
      }
      if (targetPath) {
        this.targetPath = targetPath.concat(this.expressionPath);
      } else {
        this.targetPath = this.expressionPath;
      }
    };

    ItemsScope.prototype.refreshItems = function(dataCtx) {
      this.retrieveItems(dataCtx);
      if (typeof this.onItemsRefresh === "function") {
        this.onItemsRefresh();
      }
    };

    ItemsScope.prototype.refreshItem = function(arg) {
      arg.itemsScope = this;
      if (typeof this.onItemRefresh === "function") {
        this.onItemRefresh(arg);
      }
    };

    ItemsScope.prototype.insertItem = function(arg) {
      arg.itemsScope = this;
      if (typeof this.onItemInsert === "function") {
        this.onItemInsert(arg);
      }
    };

    ItemsScope.prototype.removeItem = function(arg) {
      arg.itemsScope = this;
      if (typeof this.onItemRemove === "function") {
        this.onItemRemove(arg);
      }
    };

    ItemsScope.prototype.itemsLoadingStart = function(arg) {
      arg.itemsScope = this;
      return typeof this.onItemsLoadingStart === "function" ? this.onItemsLoadingStart(arg) : void 0;
    };

    ItemsScope.prototype.itemsLoadingEnd = function(arg) {
      arg.itemsScope = this;
      return typeof this.onItemsLoadingEnd === "function" ? this.onItemsLoadingEnd(arg) : void 0;
    };

    ItemsScope.prototype.changeCurrentItem = function(arg) {
      arg.itemsScope = this;
      if (typeof this.onCurrentItemChange === "function") {
        this.onCurrentItemChange(arg);
      }
    };

    ItemsScope.prototype.resetItemScopeMap = function() {
      this.itemScopeMap = {};
    };

    ItemsScope.prototype.getItemScope = function(item) {
      var itemId;
      itemId = cola.Entity._getEntityId(item);
      return this.itemScopeMap[itemId];
    };

    ItemsScope.prototype.regItemScope = function(itemId, itemScope) {
      this.itemScopeMap[itemId] = itemScope;
    };

    ItemsScope.prototype.unregItemScope = function(itemId) {
      delete this.itemScopeMap[itemId];
    };

    ItemsScope.prototype.findItemDomBinding = function(item) {
      var itemId, itemScopeMap, items, l, len1, matched, multiOriginItems, oi, originItems;
      itemScopeMap = this.itemScopeMap;
      items = this.items;
      originItems = this.originItems;
      multiOriginItems = originItems != null ? originItems._multiItems : void 0;
      if (items || originItems) {
        while (item) {
          if (item instanceof cola.Entity) {
            matched = item.parent === items;
            if (!matched && originItems) {
              if (multiOriginItems) {
                for (l = 0, len1 = originItems.length; l < len1; l++) {
                  oi = originItems[l];
                  if (item.parent === oi) {
                    matched = true;
                    break;
                  }
                }
              } else {
                matched = item.parent === originItems;
              }
            }
            if (matched) {
              itemId = cola.Entity._getEntityId(item);
              if (itemId) {
                return itemScopeMap[itemId];
              } else {
                return null;
              }
            }
          }
          item = item.parent;
        }
      }
      return null;
    };

    ItemsScope.prototype.isRootOfTarget = function(changedPath, targetPaths) {
      var i, isRoot, l, len1, len2, o, part, targetPart, targetPath;
      if (!targetPaths) {
        return false;
      }
      if (!changedPath) {
        return true;
      }
      for (l = 0, len1 = targetPaths.length; l < len1; l++) {
        targetPath = targetPaths[l];
        isRoot = true;
        for (i = o = 0, len2 = changedPath.length; o < len2; i = ++o) {
          part = changedPath[i];
          targetPart = targetPath[i];
          if (part !== targetPart) {
            if (targetPart === "**") {
              continue;
            } else if (targetPart === "*") {
              if (i === changedPath.length - 1) {
                continue;
              }
            }
            isRoot = false;
            break;
          }
        }
        if (isRoot) {
          return true;
        }
      }
      return false;
    };

    ItemsScope.prototype.repeatNotification = true;

    ItemsScope.prototype._processMessage = function(bindingPath, path, type, arg) {
      var allProcessed, id, itemScope, ref;
      if (this.messageTimestamp >= arg.timestamp) {
        return;
      }
      allProcessed = this.processItemsMessage(bindingPath, path, type, arg);
      if (allProcessed) {
        this.messageTimestamp = arg.timestamp;
      } else if (this.itemScopeMap) {
        itemScope = this.findItemDomBinding(arg.entity || arg.entityList);
        if (itemScope) {
          itemScope._processMessage(bindingPath, path, type, arg);
        } else {
          ref = this.itemScopeMap;
          for (id in ref) {
            itemScope = ref[id];
            itemScope._processMessage(bindingPath, path, type, arg);
          }
        }
      }
    };

    ItemsScope.prototype.isOriginItems = function(items) {
      var l, len1, originItems, ref;
      if (!this.originItems) {
        return false;
      }
      if (this.originItems === items) {
        return true;
      }
      if (this.originItems instanceof Array && this.originItems._multiItems) {
        ref = this.originItems;
        for (l = 0, len1 = ref.length; l < len1; l++) {
          originItems = ref[l];
          if (originItems === items) {
            return true;
          }
        }
      }
      return false;
    };

    ItemsScope.prototype.processItemsMessage = function(bindingPath, path, type, arg) {
      var allProcessed, i, items, parent, ref, targetPath;
      targetPath = this.targetPath ? this.targetPath.concat(this.expressionPath) : this.expressionPath;
      if (type === cola.constants.MESSAGE_REFRESH) {
        if (this.isRootOfTarget(path, targetPath)) {
          this.refreshItems();
          allProcessed = true;
        }
      } else if (type === cola.constants.MESSAGE_PROPERTY_CHANGE) {
        if (this.isRootOfTarget(path, targetPath)) {
          this.refreshItems();
          allProcessed = true;
        } else {
          parent = (ref = arg.entity) != null ? ref.parent : void 0;
          if (parent === this.items || this.isOriginItems(arg.parent)) {
            this.refreshItem(arg);
          }
        }
      } else if (type === cola.constants.MESSAGE_CURRENT_CHANGE) {
        if (arg.entityList === this.items || this.isOriginItems(arg.entityList)) {
          if (typeof this.onCurrentItemChange === "function") {
            this.onCurrentItemChange(arg);
          }
        } else if (this.isRootOfTarget(path, targetPath)) {
          this.refreshItems();
          allProcessed = true;
        }
      } else if (type === cola.constants.MESSAGE_INSERT) {
        if (arg.entityList === this.items) {
          this.insertItem(arg);
          allProcessed = true;
        } else if (this.isOriginItems(arg.entityList)) {
          this.retrieveItems();
          this.insertItem(arg);
          allProcessed = true;
        }
      } else if (type === cola.constants.MESSAGE_REMOVE) {
        if (arg.entityList === this.items) {
          this.removeItem(arg);
          allProcessed = true;
        } else if (this.isOriginItems(arg.entityList)) {
          items = this.items;
          if (items instanceof Array) {
            i = items.indexOf(arg.entity);
            if (i > -1) {
              items.splice(i, 1);
            }
          }
          this.removeItem(arg);
          allProcessed = true;
        }
      } else if (type === cola.constants.MESSAGE_LOADING_START) {
        if (this.isRootOfTarget(path, targetPath)) {
          this.itemsLoadingStart(arg);
        }
      } else if (type === cola.constants.MESSAGE_LOADING_END) {
        if (this.isRootOfTarget(path, targetPath)) {
          this.itemsLoadingEnd(arg);
        }
      }
      return allProcessed;
    };

    return ItemsScope;

  })(cola.SubScope);


  /*
  DataModel
   */

  cola.AbstractDataModel = (function() {
    AbstractDataModel.prototype.disableObserverCount = 0;

    function AbstractDataModel(model1) {
      this.model = model1;
    }

    AbstractDataModel.prototype.get = function(path, loadMode, context) {
      var aliasData, aliasHolder, callback, firstPart, i, prop, ref, ref1, rootData;
      if (!path) {
        return this._getRootData() || ((ref = this.model.parent) != null ? ref.get() : void 0);
      }
      if (this._aliasMap) {
        i = path.indexOf('.');
        firstPart = i > 0 ? path.substring(0, i) : path;
        aliasHolder = this._aliasMap[firstPart];
        if (aliasHolder) {
          aliasData = aliasHolder.data;
          if (i > 0) {
            if (loadMode && (typeof loadMode === "function" || typeof loadMode === "object")) {
              loadMode = "async";
              callback = loadMode;
            }
            return cola.Entity._evalDataPath(aliasData, path.substring(i + 1), false, loadMode, callback, context);
          } else {
            return aliasData;
          }
        }
      }
      rootData = this._rootData;
      if (rootData != null) {
        if (this.model.parent) {
          i = path.indexOf('.');
          if (i > 0) {
            prop = path.substring(0, i);
          } else {
            prop = path;
          }
          if (rootData.hasValue(prop)) {
            return rootData.get(path, loadMode, context);
          } else {
            return this.model.parent.data.get(path, loadMode, context);
          }
        } else {
          return rootData.get(path, loadMode, context);
        }
      } else {
        return (ref1 = this.model.parent) != null ? ref1.data.get(path, loadMode, context) : void 0;
      }
    };

    AbstractDataModel.prototype.set = function(path, data, context) {
      var aliasHolder, firstPart, i, p, rootData;
      if (path) {
        rootData = this._getRootData();
        if (typeof path === "string") {
          i = path.indexOf('.');
          if (i > 0) {
            firstPart = path.substring(0, i);
            if (this._aliasMap) {
              aliasHolder = this._aliasMap[firstPart];
              if (aliasHolder) {
                if (aliasHolder.data) {
                  cola.Entity._setValue(aliasHolder.data, path.substring(i + 1), data, context);
                } else {
                  throw new cola.Exception("Cannot set value to \"" + path + "\"");
                }
                return this;
              }
            }
            if (this.model.parent) {
              if (rootData.hasValue(firstPart)) {
                rootData.set(path, data, context);
              } else {
                this.model.parent.data.set(path, data, context);
              }
            } else {
              rootData.set(path, data, context);
            }
          } else {
            this._set(path, data, context);
          }
        } else {
          data = path;
          for (p in data) {
            this.set(p, data[p], context);
          }
        }
      }
      return this;
    };

    AbstractDataModel.prototype._set = function(prop, data, context) {
      var aliasHolder, dataModel, hasValue, oldAliasData, oldAliasHolder, path, property, provider, ref, rootData, rootDataType;
      rootData = this._rootData;
      hasValue = rootData.hasValue(prop);
      if ((ref = this._aliasMap) != null ? ref[prop] : void 0) {
        oldAliasHolder = this._aliasMap[prop];
        if (oldAliasHolder.data !== data) {
          oldAliasData = oldAliasHolder.data;
          delete this._aliasMap[prop];
          this.unbind(oldAliasHolder.bindingPath, oldAliasHolder);
        }
      }
      if (data != null) {
        if (data.$provider || data.$dataType) {
          if (data.$provider) {
            provider = new cola.Provider(data.$provider);
          }
          rootDataType = rootData.dataType;
          property = rootDataType.getProperty(prop);
          if (property == null) {
            property = rootDataType.addProperty({
              property: prop
            });
          }
          if (provider) {
            property.set("provider", provider);
          }
          if (data.$dataType) {
            property.set("dataType", data.$dataType);
          }
        }
      }
      if (!provider || hasValue) {
        if (data && (data instanceof cola.Entity || data instanceof cola.EntityList) && data.parent && data !== rootData._data[prop]) {
          if (this._aliasMap == null) {
            this._aliasMap = {};
          }
          path = data.getPath("always");
          dataModel = this;
          this._aliasMap[prop] = aliasHolder = {
            data: data,
            path: path,
            bindingPath: path.slice(0).concat("**"),
            _processMessage: function(bindingPath, path, type, arg) {
              var relativePath;
              relativePath = path.slice(this.path.length);
              dataModel._onDataMessage([prop].concat(relativePath), type, arg);
            }
          };
          this.bind(aliasHolder.bindingPath, aliasHolder);
          this._onDataMessage([prop], cola.constants.MESSAGE_PROPERTY_CHANGE, {
            entity: rootData,
            property: prop,
            oldValue: oldAliasData,
            value: data
          });
        } else {
          rootData.set(prop, data, context);
        }
      }
    };

    AbstractDataModel.prototype.reset = function(name) {
      var ref;
      if ((ref = this._rootData) != null) {
        ref.reset(name);
      }
      return this;
    };

    AbstractDataModel.prototype.flush = function(name, loadMode) {
      var ref;
      if ((ref = this._rootData) != null) {
        ref.flush(name, loadMode);
      }
      return this;
    };

    AbstractDataModel.prototype.bind = function(path, processor) {
      if (!this.bindingRegistry) {
        this.bindingRegistry = {
          __path: "",
          __processorMap: {}
        };
      }
      if (typeof path === "string") {
        path = path.split(".");
      }
      if (path) {
        if (this._bind(path, processor, false)) {
          this._bind(path, processor, true);
        }
      }
      return this;
    };

    AbstractDataModel.prototype._bind = function(path, processor, nonCurrent) {
      var hasNonCurrent, l, len1, node, nodePath, part, subNode;
      node = this.bindingRegistry;
      if (path) {
        for (l = 0, len1 = path.length; l < len1; l++) {
          part = path[l];
          if (!nonCurrent && part.charCodeAt(0) === 33) {
            hasNonCurrent = true;
            part = part.substring(1);
          }
          subNode = node[part];
          if (subNode == null) {
            nodePath = !node.__path ? part : node.__path + "." + part;
            node[part] = subNode = {
              __path: nodePath,
              __processorMap: {}
            };
          }
          node = subNode;
        }
        if (processor.id == null) {
          processor.id = cola.uniqueId();
        }
        node.__processorMap[processor.id] = processor;
      }
      return hasNonCurrent;
    };

    AbstractDataModel.prototype.unbind = function(path, processor) {
      if (!this.bindingRegistry) {
        return;
      }
      if (typeof path === "string") {
        path = path.split(".");
      }
      if (path) {
        if (this._unbind(path, processor, false)) {
          this._unbind(path, processor, true);
        }
      }
      return this;
    };

    AbstractDataModel.prototype._unbind = function(path, processor, nonCurrent) {
      var hasNonCurrent, l, len1, node, part;
      node = this.bindingRegistry;
      for (l = 0, len1 = path.length; l < len1; l++) {
        part = path[l];
        if (!nonCurrent && part.charCodeAt(0) === 33) {
          hasNonCurrent = true;
          part = part.substring(1);
        }
        node = node[part];
        if (node == null) {
          break;
        }
      }
      if (node != null) {
        delete node.__processorMap[processor.id];
      }
      return hasNonCurrent;
    };

    AbstractDataModel.prototype.disableObservers = function() {
      if (this.disableObserverCount < 0) {
        this.disableObserverCount = 1;
      } else {
        this.disableObserverCount++;
      }
      return this;
    };

    AbstractDataModel.prototype.enableObservers = function() {
      if (this.disableObserverCount < 1) {
        this.disableObserverCount = 0;
      } else {
        this.disableObserverCount--;
      }
      return this;
    };

    AbstractDataModel.prototype.notifyObservers = function() {
      var ref;
      if ((ref = this._rootData) != null) {
        ref.notifyObservers();
      }
      return this;
    };

    AbstractDataModel.prototype._onDataMessage = function(path, type, arg) {
      var anyChildNode, anyPropNode, i, l, lastIndex, len1, node, oldScope, part;
      if (arg == null) {
        arg = {};
      }
      if (!this.bindingRegistry) {
        return;
      }
      if (this.disableObserverCount > 0) {
        return;
      }
      oldScope = cola.currentScope;
      cola.currentScope = this;
      try {
        if (arg.timestamp == null) {
          arg.timestamp = cola.sequenceNo();
        }
        if (path) {
          node = this.bindingRegistry;
          lastIndex = path.length - 1;
          for (i = l = 0, len1 = path.length; l < len1; i = ++l) {
            part = path[i];
            if (i === lastIndex) {
              anyPropNode = node["*"];
            }
            if (anyPropNode) {
              this._processDataMessage(anyPropNode, path, type, arg);
            }
            anyChildNode = node["**"];
            if (anyChildNode) {
              this._processDataMessage(anyChildNode, path, type, arg);
            }
            node = node[part];
            if (!node) {
              break;
            }
          }
        } else {
          node = this.bindingRegistry;
          anyPropNode = node["*"];
          if (anyPropNode) {
            this._processDataMessage(anyPropNode, null, type, arg);
          }
          anyChildNode = node["**"];
          if (anyChildNode) {
            this._processDataMessage(anyChildNode, null, type, arg);
          }
        }
        if (node) {
          this._processDataMessage(node, path, type, arg, true);
        }
      } finally {
        cola.currentScope = oldScope;
      }
    };

    AbstractDataModel.prototype._processDataMessage = function(node, path, type, arg, notifyChildren) {
      var id, notifyChildren2, part, processor, processorMap, subNode;
      processorMap = node.__processorMap;
      for (id in processorMap) {
        processor = processorMap[id];
        if (!processor.disabled && (!(processor.timestamp >= arg.timestamp) || processor.repeatNotification)) {
          processor.timestamp = arg.timestamp;
          processor._processMessage(node.__path, path, type, arg);
        }
      }
      if (notifyChildren) {
        notifyChildren2 = !((cola.constants.MESSAGE_EDITING_STATE_CHANGE <= type && type <= cola.constants.MESSAGE_VALIDATION_STATE_CHANGE)) && !((cola.constants.MESSAGE_LOADING_START <= type && type <= cola.constants.MESSAGE_LOADING_END));
        if (type === cola.constants.MESSAGE_CURRENT_CHANGE) {
          type = cola.constants.MESSAGE_REFRESH;
        }
        for (part in node) {
          subNode = node[part];
          if (subNode && (part === "**" || notifyChildren2) && part !== "__processorMap" && part !== "__path") {
            this._processDataMessage(subNode, path, type, arg, true);
          }
        }
      }
    };

    return AbstractDataModel;

  })();

  cola.DataModel = (function(superClass) {
    extend(DataModel, superClass);

    function DataModel() {
      return DataModel.__super__.constructor.apply(this, arguments);
    }

    DataModel.prototype._createRootData = function(rootDataType) {
      return new cola.Entity(null, rootDataType);
    };

    DataModel.prototype._getRootData = function() {
      var dataModel, rootData;
      if (this._rootData == null) {
        if (this._rootDataType == null) {
          this._rootDataType = new cola.EntityDataType();
        }
        this._rootData = rootData = this._createRootData(this._rootDataType);
        rootData.state = cola.Entity.STATE_NEW;
        dataModel = this;
        rootData._setDataModel(dataModel);
      }
      return this._rootData;
    };

    DataModel.prototype.describe = function(property, config) {
      var dataType, propertyConfig, propertyDef, propertyName, ref;
      this._getRootData();
      if (typeof property === "string") {
        propertyDef = (ref = this._rootDataType) != null ? ref.getProperty(property) : void 0;
        if (config) {
          if (!propertyDef) {
            propertyDef = this._rootDataType.addProperty({
              property: property
            });
          }
          if (typeof config === "string") {
            dataType = this.definition(config);
            if (!dataType) {
              throw new cola.Exception("Unrecognized DataType \"" + config + "\".");
            }
            propertyDef.set("dataType", dataType);
          } else if (config instanceof cola.DataType) {
            propertyDef.set("dataType", config);
          } else {
            propertyDef.set(config);
          }
        }
      } else if (property) {
        config = property;
        for (propertyName in config) {
          propertyConfig = config[propertyName];
          this.describe(propertyName, propertyConfig);
        }
      }
    };

    DataModel.prototype.getProperty = function(path) {
      var dataModel, dataType, i, path1, path2, ref, ref1, rootDataType;
      i = path.indexOf(".");
      if (i > 0) {
        path1 = path.substring(0, i);
        path2 = path.substring(i + 1);
      } else {
        path1 = null;
        path2 = path;
      }
      dataModel = this;
      while (dataModel) {
        rootDataType = dataModel._rootDataType;
        if (rootDataType) {
          if (path1) {
            dataType = (ref = rootDataType.getProperty(path1)) != null ? ref.get("dataType") : void 0;
          } else {
            dataType = rootDataType;
          }
          if (dataType) {
            break;
          }
        }
        dataModel = (ref1 = dataModel.model.parent) != null ? ref1.data : void 0;
      }
      return dataType != null ? dataType.getProperty(path2) : void 0;
    };

    DataModel.prototype.getDataType = function(path) {
      var dataType, property;
      property = this.getProperty(path);
      dataType = property != null ? property.get("dataType") : void 0;
      return dataType;
    };

    DataModel.prototype.definition = function(name) {
      var definition, ref;
      definition = (ref = this._definitionStore) != null ? ref[name] : void 0;
      if (definition) {
        if (!(definition instanceof cola.Definition)) {
          definition = new cola.EntityDataType(definition);
          this._definitionStore[name] = definition;
        }
      } else {
        definition = cola.DataType.defaultDataTypes[name];
      }
      return definition;
    };

    DataModel.prototype.regDefinition = function(name, definition) {
      var store;
      if (name instanceof cola.Definition) {
        definition = name;
        name = name._name;
      }
      if (!name) {
        throw new cola.Exception("Attribute \"name\" cannot be emtpy.");
      }
      if (definition._scope && definition._scope !== this.model) {
        throw new cola.Exception("DataType(" + definition._name + ") is already belongs to anthor Model.");
      }
      store = this._definitionStore;
      if (store == null) {
        this._definitionStore = store = {};
      } else if (store[name]) {
        throw new cola.Exception("Duplicated Definition name \"" + name + "\".");
      }
      store[name] = definition;
      return this;
    };

    DataModel.prototype.unregDefinition = function(name) {
      var definition;
      if (this._definitionStore) {
        definition = this._definitionStore[name];
        delete this._definitionStore[name];
      }
      return definition;
    };

    DataModel.prototype.addEntityListener = function(listener) {
      if (this._entityListeners == null) {
        this._entityListeners = [];
      }
      this._entityListeners.push(listener);
    };

    DataModel.prototype.removeEntityListener = function(listener) {
      var i;
      if (!this._entityListeners) {
        return;
      }
      if (listener) {
        i = this._entityListeners.indexOf(listener);
        if (i > -1) {
          this._entityListeners.splice(i, 1);
        }
      }
    };

    DataModel.prototype.onEntityAttach = function(entity) {
      var l, len1, listener, ref;
      if (this._entityListeners) {
        ref = this._entityListeners;
        for (l = 0, len1 = ref.length; l < len1; l++) {
          listener = ref[l];
          listener.onEntityAttach(entity);
        }
      }
    };

    DataModel.prototype.onEntityDetach = function(entity) {
      var l, len1, listener, ref;
      if (this._entityListeners) {
        ref = this._entityListeners;
        for (l = 0, len1 = ref.length; l < len1; l++) {
          listener = ref[l];
          listener.onEntityDetach(entity);
        }
      }
    };

    return DataModel;

  })(cola.AbstractDataModel);

  cola.AliasDataModel = (function(superClass) {
    extend(AliasDataModel, superClass);

    function AliasDataModel(model1, alias1, dataType1) {
      var parentModel;
      this.model = model1;
      this.alias = alias1;
      this.dataType = dataType1;
      parentModel = this.model.parent;
      while (parentModel) {
        if (parentModel.data) {
          this.parent = parentModel.data;
          break;
        }
        parentModel = parentModel.parent;
      }
    }

    AliasDataModel.prototype.getTargetData = function() {
      return this._targetData;
    };

    AliasDataModel.prototype.setTargetData = function(data, silence) {
      var oldData;
      oldData = this._targetData;
      if (oldData === data) {
        return;
      }
      this._targetData = data;
      if (data && (data instanceof cola.Entity || data instanceof cola.EntityList)) {
        this._targetPath = data.getPath();
      }
      if (!silence) {
        this._onDataMessage([this.alias], cola.constants.MESSAGE_PROPERTY_CHANGE, {
          entity: null,
          property: this.alias,
          value: data,
          oldValue: oldData
        });
      }
    };

    AliasDataModel.prototype.describe = function(property, config) {
      if (property === this.alias) {
        return AliasDataModel.__super__.describe.call(this, property, config);
      } else {
        return this.parent.describe(property, config);
      }
    };

    AliasDataModel.prototype.getProperty = function(path) {
      var dataType, i, property;
      i = path.indexOf(".");
      if (i > 0) {
        if (path.substring(0, i) === this.alias) {
          dataType = this._targetData instanceof cola.Entity || this._targetData instanceof cola.EntityList ? this._targetData.dataType : null;
          if (dataType) {
            property = dataType.getProperty(path.substring(i + 1));
            dataType = property != null ? property.get("dataType") : void 0;
          }
          return dataType;
        } else {
          return this.parent.getDataType(path);
        }
      } else if (path === this.alias) {
        if (this._targetData instanceof cola.Entity || this._targetData instanceof cola.EntityList) {
          return this._targetData.dataType;
        } else {
          return null;
        }
      } else {
        return this.parent.getProperty(path);
      }
    };

    AliasDataModel.prototype.getDataType = function(path) {
      var dataType, i, property, ref;
      i = path.indexOf(".");
      if (i > 0) {
        if (path.substring(0, i) === this.alias) {
          if (this._rootDataType) {
            property = (ref = this._rootDataType) != null ? ref.getProperty(path.substring(i + 1)) : void 0;
            dataType = property != null ? property.get("dataType") : void 0;
          }
          return dataType;
        } else {
          return this.parent.getDataType(path);
        }
      } else if (path === this.alias) {
        return this.dataType;
      } else {
        return this.parent.getDataType(path);
      }
    };

    AliasDataModel.prototype.definition = function(name) {
      return this.parent.definition(name);
    };

    AliasDataModel.prototype.regDefinition = function(definition) {
      return this.parent.regDefinition(definition);
    };

    AliasDataModel.prototype.unregDefinition = function(definition) {
      return this.parent.unregDefinition(definition);
    };

    AliasDataModel.prototype._bind = function(path, processor, nonCurrent) {
      var hasNonCurrent, i;
      hasNonCurrent = AliasDataModel.__super__._bind.call(this, path, processor, nonCurrent);
      i = path.indexOf(".");
      if (i > 0) {
        if (path.substring(0, i) !== this.alias) {
          this.model.watchAllMessages();
        }
      } else if (path !== this.alias) {
        this.model.watchAllMessages();
      }
      return hasNonCurrent;
    };

    AliasDataModel.prototype._processMessage = function(bindingPath, path, type, arg) {
      var i, l, len1, matching, part, relativePath, targetPart, targetPath;
      this._onDataMessage(path, type, arg);
      targetPath = this._targetPath;
      if ((targetPath != null ? targetPath.length : void 0) && targetPath.length < (path != null ? path.length : void 0)) {
        matching = true;
        for (i = l = 0, len1 = targetPath.length; l < len1; i = ++l) {
          targetPart = targetPath[i];
          part = path[i];
          if (part && part.charCodeAt(0) === 33) {
            part = part.substring(1);
          }
          if (part !== targetPart) {
            matching = false;
            break;
          }
        }
        if (matching) {
          relativePath = path.slice(targetPath.length);
          this._onDataMessage([this.alias].concat(relativePath), type, arg);
        }
      }
    };

    AliasDataModel.prototype.get = function(path, loadMode, context) {
      var alias, aliasLen, c, targetData;
      alias = this.alias;
      aliasLen = alias.length;
      if (path.substring(0, aliasLen) === alias) {
        c = path.charCodeAt(aliasLen);
        if (c === 46) {
          if (path.indexOf(".") > 0) {
            targetData = this._targetData;
            if (targetData instanceof cola.Entity) {
              return targetData.get(path.substring(aliasLen + 1), loadMode, context);
            } else if (targetData && typeof targetData === "object") {
              return targetData[path.substring(aliasLen + 1)];
            }
          }
        } else if (isNaN(c)) {
          return this._targetData;
        }
      }
      return this.parent.get(path, loadMode, context);
    };

    AliasDataModel.prototype.set = function(path, data, context) {
      var alias, aliasLen, c, ref;
      alias = this.alias;
      aliasLen = alias.length;
      if (path.substring(0, aliasLen) === alias) {
        c = path.charCodeAt(aliasLen);
        if (c === 46) {
          if (path.indexOf(".") > 0) {
            if ((ref = this._targetData) != null) {
              ref.set(path.substring(aliasLen + 1), data, context);
            }
            return this;
          }
        } else if (isNaN(c)) {
          this.setTargetData(data);
          return this;
        }
      }
      this.parent.set(path, data, context);
      return this;
    };

    AliasDataModel.prototype.dataType = function(path) {
      return this.parent.dataType(path);
    };

    AliasDataModel.prototype.regDefinition = function(name, definition) {
      this.parent.regDefinition(name, definition);
      return this;
    };

    AliasDataModel.prototype.unregDefinition = function(name) {
      return this.parent.unregDefinition(name);
    };

    AliasDataModel.prototype.flush = function(path, loadMode) {
      var alias, c, ref, ref1;
      alias = this.alias;
      if (path.substring(0, alias.length) === alias) {
        c = path.charCodeAt(1);
        if (c === 46) {
          if ((ref = this._targetData) != null) {
            ref.flush(path.substring(alias.length + 1), loadMode);
          }
          return this;
        } else if (isNaN(c)) {
          if ((ref1 = this._targetData) != null) {
            ref1.flush(loadMode);
          }
          return this;
        }
      }
      this.parent.flush(path, loadMode);
      return this;
    };

    AliasDataModel.prototype.disableObservers = function() {
      this.parent.disableObservers();
      return this;
    };

    AliasDataModel.prototype.enableObservers = function() {
      this.parent.enableObservers();
      return this;
    };

    AliasDataModel.prototype.notifyObservers = function() {
      this.parent.notifyObservers();
      return this;
    };

    return AliasDataModel;

  })(cola.AbstractDataModel);

  cola.ItemDataModel = (function(superClass) {
    extend(ItemDataModel, superClass);

    function ItemDataModel() {
      return ItemDataModel.__super__.constructor.apply(this, arguments);
    }

    ItemDataModel.prototype.getIndex = function() {
      return this._index;
    };

    ItemDataModel.prototype.setIndex = function(index, silence) {
      this._index = index;
      if (!silence) {
        this._onDataMessage([cola.constants.REPEAT_INDEX], cola.constants.MESSAGE_PROPERTY_CHANGE, {
          entity: null,
          property: cola.constants.REPEAT_INDEX,
          value: index
        });
      }
    };

    ItemDataModel.prototype.get = function(path, loadMode, context) {
      if (path === cola.constants.REPEAT_INDEX) {
        return this.getIndex();
      } else {
        return ItemDataModel.__super__.get.call(this, path, loadMode, context);
      }
    };

    ItemDataModel.prototype.set = function(path, data, context) {
      if (path === cola.constants.REPEAT_INDEX) {
        this.setIndex(data);
      } else {
        ItemDataModel.__super__.set.call(this, path, data, context);
      }
    };

    return ItemDataModel;

  })(cola.AliasDataModel);


  /*
  Root Model
   */

  new cola.Model(cola.constants.DEFAULT_PATH);


  /*
  Function
   */

  cola.data = function(config) {
    var dataType, k, name, provider, v;
    if (!config) {
      return config;
    }
    if (config.provider) {
      provider = config.provider;
    } else {
      provider = {};
      for (k in config) {
        v = config[k];
        if (k !== "dataType") {
          provider[k] = v;
        }
      }
    }
    dataType = config.dataType;
    if (dataType) {
      if (typeof dataType === "string") {
        name = dataType;
        dataType = cola.currentScope.dataType(name);
        if (!dataType) {
          throw new cola.Exception("Unrecognized DataType \"" + name + "\".");
        }
      } else if (!(dataType instanceof cola.DataType)) {
        dataType = new cola.EntityDataType(dataType);
      }
    }
    return {
      $dataType: dataType,
      $provider: provider
    };
  };


  /*
  Element binding
   */

  cola.ElementAttrBinding = (function() {
    function ElementAttrBinding(element1, attr1, expression1, scope) {
      var l, len1, path, paths;
      this.element = element1;
      this.attr = attr1;
      this.expression = expression1;
      this.scope = scope;
      this.paths = paths = this.expression.paths;
      if (!paths && this.expression.hasCallStatement) {
        this.paths = paths = ["**"];
        this.watchingMoreMessage = this.expression.hasCallStatement;
      }
      if (paths) {
        for (l = 0, len1 = paths.length; l < len1; l++) {
          path = paths[l];
          scope.data.bind(path, this);
        }
      }
    }

    ElementAttrBinding.prototype.destroy = function() {
      var l, len1, path, paths;
      paths = this.paths;
      if (paths) {
        for (l = 0, len1 = paths.length; l < len1; l++) {
          path = paths[l];
          this.scope.data.unbind(path, this);
        }
      }
    };

    ElementAttrBinding.prototype._processMessage = function(bindingPath, path, type) {
      if ((cola.constants.MESSAGE_REFRESH <= type && type <= cola.constants.MESSAGE_CURRENT_CHANGE) || this.watchingMoreMessage) {
        this.refresh();
      }
    };

    ElementAttrBinding.prototype.evaluate = function(dataCtx) {
      if (dataCtx == null) {
        dataCtx = {};
      }
      return this.expression.evaluate(this.scope, "async", dataCtx);
    };

    ElementAttrBinding.prototype._refresh = function() {
      var element;
      value = this.evaluate();
      element = this.element;
      element._duringBindingRefresh = true;
      try {
        element.set(this.attr, value);
      } finally {
        element._duringBindingRefresh = false;
      }
    };

    ElementAttrBinding.prototype.refresh = function() {
      if (!this._refresh) {
        return;
      }
      if (this.delay) {
        cola.util.delay(this, "refresh", 100, function() {
          this._refresh();
        });
      } else {
        this._refresh();
      }
    };

    return ElementAttrBinding;

  })();

  cola.submit = function(options, callback) {
    var data, filter, originalOptions, p, v;
    originalOptions = options;
    options = {};
    for (p in originalOptions) {
      v = originalOptions[p];
      options[p] = v;
    }
    data = options.data;
    if (data) {
      if (!(data instanceof cola.Entity || data instanceof cola.EntityList)) {
        throw new cola.Exception("Invalid submit data.");
      }
      if (this.dataFilter) {
        filter = cola.submit.filter[this.dataFilter];
        data = filter ? filter(data) : data;
      }
    }
    if (data || options.alwaysSubmit) {
      if (options.parameter) {
        options.data = {
          data: data,
          parameter: options.parameter
        };
      } else {
        options.data = data;
      }
      jQuery.post(options.url, options.data).done(function(result) {
        cola.callback(callback, true, result);
      }).fail(function(result) {
        cola.callback(callback, true, result);
      });
      return true;
    } else {
      return false;
    }
  };

  cola.submit.filter = {
    "dirty": function(data) {
      var filtered;
      if (data instanceof cola.EntityList) {
        filtered = [];
        data.each(function(entity) {
          if (entity.state !== cola.Entity.STATE_NONE) {
            filtered.push(entity);
          }
        });
      } else if (data.state !== cola.Entity.STATE_NONE) {
        filtered = data;
      }
      return filtered;
    },
    "child-dirty": function(data) {
      return data;
    },
    "dirty-tree": function(data) {
      return data;
    }
  };

  defaultActionTimestamp = 0;

  cola.defaultAction = function(name, fn) {
    var n;
    if (!name) {
      return;
    }
    if (typeof name === "string" && typeof fn === "function") {
      cola.defaultAction[name] = fn;
    } else if (typeof name === "object") {
      for (n in name) {
        if (name.hasOwnProperty(n)) {
          cola.defaultAction[n] = name[n];
        }
      }
    }
    defaultActionTimestamp = cola.uniqueId();
  };

  cola.Chain = (function() {
    function Chain(data) {
      var name;
      this._data = data;
      if (cola.Chain.prototype.timestamp !== defaultActionTimestamp) {
        cola.Chain.prototype.timestamp = defaultActionTimestamp;
        for (name in cola.defaultAction) {
          if (!cola.Chain.prototype[name] && cola.defaultAction.hasOwnProperty(name) && name !== "chain") {
            (function(name) {
              return cola.Chain.prototype[name] = function() {
                var args, ref;
                args = 1 <= arguments.length ? slice.call(arguments, 0) : [];
                this._data = (ref = cola.defaultAction)[name].apply(ref, [this._data].concat(slice.call(args)));
                return this;
              };
            })(name);
          }
        }
      }
    }

    return Chain;

  })();

  cola.defaultAction.chain = function(data) {
    return new cola.Chain(data);
  };

  cola.defaultAction["default"] = function(value, defaultValue) {
    if (defaultValue == null) {
      defaultValue = "";
    }
    return value || defaultValue;
  };

  cola.defaultAction["int"] = function(value) {
    return parseInt(value, 10) || 0;
  };

  cola.defaultAction["float"] = function(value) {
    return parseFloat(value) || 0;
  };

  cola.defaultAction["is"] = function(value) {
    return !!value;
  };

  cola.defaultAction["bool"] = cola.defaultAction.is;

  cola.defaultAction["not"] = function(value) {
    return !value;
  };

  cola.defaultAction.isEmpty = function(value) {
    if (value instanceof Array) {
      return value.length === 0;
    } else if (value instanceof cola.EntityList) {
      return value.entityCount === 0;
    } else if (typeof value === "string") {
      return value === "";
    } else {
      return !value;
    }
  };

  cola.defaultAction.isNotEmpty = function(value) {
    return !cola.defaultAction.isEmpty(value);
  };

  cola.defaultAction.len = function(value) {
    if (!value) {
      return 0;
    }
    if (value instanceof Array) {
      return value.length;
    }
    if (value instanceof cola.EntityList) {
      return value.entityCount;
    }
    return 0;
  };

  cola.defaultAction["upperCase"] = function(value) {
    return value != null ? value.toUpperCase() : void 0;
  };

  cola.defaultAction["lowerCase"] = function(value) {
    return value != null ? value.toLowerCase() : void 0;
  };

  cola.defaultAction["capitalize"] = function(value) {
    return cola.util.capitalize(value);
  };

  cola.defaultAction.resource = function() {
    var key, params;
    key = arguments[0], params = 2 <= arguments.length ? slice.call(arguments, 1) : [];
    return cola.resource.apply(cola, [key].concat(slice.call(params)));
  };

  _matchValue = function(value, propFilter) {
    if (propFilter.strict) {
      if (!propFilter.caseSensitive && typeof propFilter.value === "string") {
        return (value + "").toLowerCase() === propFilter.value;
      } else {
        return value === propFilter.value;
      }
    } else {
      if (!propFilter.caseSensitive) {
        return (value + "").toLowerCase().indexOf(propFilter.value) > -1;
      } else {
        return (value + "").indexOf(propFilter.value) > -1;
      }
    }
  };

  cola.defaultAction.filter = function(collection, criteria, option) {
    criteria = cola._trimCriteria(criteria, option);
    return cola.util.filter(collection, criteria, option);
  };

  cola.defaultAction.sort = cola.util.sort;

  cola.defaultAction["top"] = function(collection, top) {
    var i, items;
    if (top == null) {
      top = 1;
    }
    if (!collection) {
      return null;
    }
    if (top < 0) {
      return collection;
    }
    items = [];
    items.$origin = collection.$origin || collection;
    i = 0;
    cola.each(collection, function(item) {
      i++;
      items.push(item);
      return i < top;
    });
    return items;
  };

  cola.defaultAction.formatDate = cola.util.formatDate;

  cola.defaultAction.formatNumber = cola.util.formatNumber;

  cola.defaultAction.format = cola.util.format;

  _numberWords = ["zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen"];

  cola.defaultAction.number2Word = function(number) {
    return _numberWords[number];
  };

  cola.defaultAction.backgroundImage = function(url) {
    if (url) {
      return "url(" + url + ")";
    } else {
      return "none";
    }
  };

  cola.AjaxServiceInvoker = (function() {
    function AjaxServiceInvoker(ajaxService1, invokerOptions1) {
      this.ajaxService = ajaxService1;
      this.invokerOptions = invokerOptions1;
      this.callbacks = [];
    }

    AjaxServiceInvoker.prototype.invokeCallback = function(success, result) {
      var callback, callbacks, l, len1;
      this.invoking = false;
      callbacks = this.callbacks;
      this.callbacks = [];
      for (l = 0, len1 = callbacks.length; l < len1; l++) {
        callback = callbacks[l];
        cola.callback(callback, success, result);
      }
    };

    AjaxServiceInvoker.prototype._internalInvoke = function(async) {
      var ajaxService, invokerOptions, options, p, retValue, v;
      if (async == null) {
        async = true;
      }
      ajaxService = this.ajaxService;
      invokerOptions = this.invokerOptions;
      retValue = void 0;
      options = {};
      for (p in invokerOptions) {
        v = invokerOptions[p];
        options[p] = v;
      }
      options.async = async;
      if (options.sendJson) {
        options.data = JSON.stringify(options.data);
      }
      if (ajaxService.getListeners("beforeSend")) {
        if (ajaxService.fire("beforeSend", ajaxService, {
          options: options
        }) === false) {
          return;
        }
      }
      if (this._beforeSend) {
        this._beforeSend(options);
      }
      jQuery.ajax(options).done((function(_this) {
        return function(result) {
          result = ajaxService.translateResult(result, options);
          _this.invokeCallback(true, result);
          if (ajaxService.getListeners("success")) {
            ajaxService.fire("success", ajaxService, {
              options: options,
              result: result
            });
          }
          if (ajaxService.getListeners("complete")) {
            ajaxService.fire("complete", ajaxService, {
              success: true,
              options: options,
              result: result
            });
          }
          retValue = result;
        };
      })(this)).fail((function(_this) {
        return function(xhr) {
          var error;
          error = xhr.responseJSON;
          _this.invokeCallback(false, error);
          ajaxService.fire("error", ajaxService, {
            options: options,
            xhr: xhr,
            error: error
          });
          ajaxService.fire("complete", ajaxService, {
            success: false,
            xhr: xhr,
            options: options,
            error: error
          });
        };
      })(this));
      return retValue;
    };

    AjaxServiceInvoker.prototype.invokeAsync = function(callback) {
      this.callbacks.push(callback);
      if (this.invoking) {
        return false;
      }
      this.invoking = true;
      this._internalInvoke();
      return true;
    };

    AjaxServiceInvoker.prototype.invokeSync = function(callback) {
      if (this.invoking) {
        throw new cola.Exception("Cannot perform synchronized request during an asynchronized request executing. [" + this.url + "]");
      }
      this.callbacks.push(callback);
      return this._internalInvoke(false);
    };

    return AjaxServiceInvoker;

  })();

  cola.AjaxService = (function(superClass) {
    extend(AjaxService, superClass);

    AjaxService.attributes = {
      url: null,
      method: null,
      parameter: null,
      ajaxOptions: null
    };

    AjaxService.events = {
      beforeSend: null,
      complete: null,
      success: null,
      error: null
    };

    function AjaxService(config) {
      if (typeof config === "string") {
        config = {
          url: config
        };
      }
      AjaxService.__super__.constructor.call(this, config);
    }

    AjaxService.prototype.getUrl = function() {
      return this._url;
    };

    AjaxService.prototype.getInvokerOptions = function(context) {
      var ajaxOptions, options, p, v;
      options = {};
      ajaxOptions = this._ajaxOptions;
      if (ajaxOptions) {
        for (p in ajaxOptions) {
          v = ajaxOptions[p];
          options[p] = v;
        }
      }
      options.url = this.getUrl(context);
      if (this._method) {
        options.method = this._method;
      }
      options.data = this._parameter;
      return options;
    };

    AjaxService.prototype.getInvoker = function(context) {
      return new cola.AjaxServiceInvoker(this, this.getInvokerOptions(context));
    };

    AjaxService.prototype.translateResult = function(result, invokerOptions) {
      return result;
    };

    return AjaxService;

  })(cola.Definition);

  cola.ProviderInvoker = (function(superClass) {
    extend(ProviderInvoker, superClass);

    function ProviderInvoker() {
      return ProviderInvoker.__super__.constructor.apply(this, arguments);
    }

    ProviderInvoker.prototype._replaceSysParams = function(options) {
      var changed, data, l, len1, match, matches, name, p, url, v;
      url = options.originUrl || options.url;
      matches = url.match(/{{\$[\w-]+}}/g);
      if (matches) {
        if (options.originUrl == null) {
          options.originUrl = url;
        }
        for (l = 0, len1 = matches.length; l < len1; l++) {
          match = matches[l];
          name = match.substring(2, match.length - 1);
          if (name) {
            url = url.replace(match, this[name] || "");
            options.url = url;
            changed = true;
          }
        }
      }
      data = options.originData || options.data;
      if (data) {
        for (p in data) {
          v = data[p];
          if (typeof v === "string") {
            if (v.charCodeAt(0) === 123 && v.match(/^{{\$[\w-]+}}$/)) {
              if (options.originData == null) {
                options.originData = $.extend(data, null);
              }
              data[p] = this[v.substring(2, v.length - 1)];
              changed = true;
            }
          }
        }
      }
      return changed;
    };

    ProviderInvoker.prototype.applyPagingParameters = function(options) {
      if (!this._replaceSysParams(options)) {
        if (options.data == null) {
          options.data = {};
        }
        if (cola.setting("pagingParamStyle") === "from") {
          options.data.from = this.from;
          options.data.limit = this.limit + (this.detectEnd ? 1 : 0);
        } else {
          options.data.pageSize = this.pageSize;
          options.data.pageNo = this.pageNo;
        }
      }
    };

    ProviderInvoker.prototype._beforeSend = function(options) {
      if (!this.pageNo >= 1) {
        this.pageNo = 1;
      }
      this.from = this.pageSize * (this.pageNo - 1);
      this.limit = this.pageSize;
      if (this.pageSize) {
        this.applyPagingParameters(options);
      }
    };

    return ProviderInvoker;

  })(cola.AjaxServiceInvoker);

  _SYS_PARAMS = ["$pageNo", "$pageSize", "$from", "$limit"];

  _ExpressionDataModel = (function(superClass) {
    extend(_ExpressionDataModel, superClass);

    function _ExpressionDataModel(model, entity1) {
      this.entity = entity1;
      _ExpressionDataModel.__super__.constructor.call(this, model);
    }

    _ExpressionDataModel.prototype.get = function(path, loadMode, context) {
      var ref;
      if (path.charCodeAt(0) === 64) {
        return this.entity.get(path.substring(1));
      } else {
        return (ref = this.model.parent) != null ? ref.data.get(path, loadMode, context) : void 0;
      }
    };

    _ExpressionDataModel.prototype.set = cola._EMPTY_FUNC;

    _ExpressionDataModel.prototype._processMessage = cola._EMPTY_FUNC;

    _ExpressionDataModel.prototype.getDataType = cola._EMPTY_FUNC;

    _ExpressionDataModel.prototype.getProperty = cola._EMPTY_FUNC;

    _ExpressionDataModel.prototype.flush = cola._EMPTY_FUNC;

    return _ExpressionDataModel;

  })(cola.AbstractDataModel);

  _ExpressionScope = (function(superClass) {
    extend(_ExpressionScope, superClass);

    function _ExpressionScope(parent1, entity1) {
      this.parent = parent1;
      this.entity = entity1;
      this.data = new _ExpressionDataModel(this, this.entity);
      this.action = this.parent.action;
    }

    return _ExpressionScope;

  })(cola.SubScope);

  cola.Provider = (function(superClass) {
    extend(Provider, superClass);

    function Provider() {
      return Provider.__super__.constructor.apply(this, arguments);
    }

    Provider.attributes = {
      loadMode: {
        defaultValue: "lazy"
      },
      pageSize: null,
      detectEnd: null
    };

    Provider.prototype.getUrl = function(context) {
      var l, len1, match, matches, url;
      url = this._url;
      matches = url.match(/{{.+}}/g);
      if (matches) {
        if (context.expressionScope == null) {
          context.expressionScope = new _ExpressionScope(this._scope, context.data);
        }
        for (l = 0, len1 = matches.length; l < len1; l++) {
          match = matches[l];
          url = url.replace(match, this._evalParamValue(match, context));
        }
      }
      return url;
    };

    Provider.prototype.getInvoker = function(context) {
      var provider;
      provider = new cola.ProviderInvoker(this, this.getInvokerOptions(context));
      provider.pageSize = this._pageSize;
      provider.detectEnd = this._detectEnd;
      return provider;
    };

    Provider.prototype._evalParamValue = function(expr, context) {
      var expression;
      if (expr.charCodeAt(0) === 123) {
        if (expr.match(/^{{.+}}$/)) {
          expression = expr.substring(2, expr.length - 2);
          if (_SYS_PARAMS.indexOf(expression) < 0) {
            expression = cola._compileExpression(expression);
            if (expression) {
              return expression.evaluate(context.expressionScope, "never");
            }
          }
        }
      }
      return expr;
    };

    Provider.prototype.getInvokerOptions = function(context) {
      var oldParameter, options, p, parameter, v;
      options = Provider.__super__.getInvokerOptions.call(this, context);
      parameter = options.data;
      if (parameter != null) {
        if (context.expressionScope == null) {
          context.expressionScope = new _ExpressionScope(this._scope, context.data);
        }
        if (typeof parameter === "string") {
          parameter = this._evalParamValue(parameter, context);
        } else {
          if (typeof parameter === "function") {
            parameter = parameter(this, context);
          }
          if (typeof parameter === "object") {
            oldParameter = parameter;
            parameter = {};
            for (p in oldParameter) {
              v = oldParameter[p];
              if (typeof v === "string") {
                v = this._evalParamValue(v, context);
              }
              parameter[p] = v;
            }
          }
        }
      }
      if (parameter == null) {
        parameter = {};
      } else if (!(parameter instanceof Object)) {
        parameter = {
          parameter: parameter
        };
      }
      options.data = parameter;
      if (options.dataType == null) {
        options.dataType = "json";
      }
      return options;
    };

    Provider.prototype.translateResult = function(result, invokerOptions) {
      if (this._detectEnd && result instanceof Array) {
        if (result.length >= this._pageSize) {
          result = result.slice(0, this._pageSize);
        } else {
          result = {
            $entityCount: (invokerOptions.data.from || 0) + result.length,
            $data: result
          };
        }
      }
      return result;
    };

    return Provider;

  })(cola.AjaxService);

  _$ = $();

  _$.length = 1;

  this.$fly = function(dom) {
    _$[0] = dom;
    return _$;
  };

  cola.util.setText = function(dom, text) {
    if (text == null) {
      text = "";
    }
    if (cola.browser.mozilla) {
      if (typeof text === "string") {
        text = text.replace(/&/g, "&amp;").replace(/>/g, "&gt;").replace(/</g, "&lt;").replace(/\n/g, "<br>");
      }
      dom.innerHTML = text;
    } else {
      dom.innerText = text;
    }
  };

  doms = {};

  cola.util.cacheDom = function(ele) {
    cola._ignoreNodeRemoved = true;
    if (!doms.hiddenDiv) {
      doms.hiddenDiv = $.xCreate({
        tagName: "div",
        id: "_hidden_div",
        style: {
          display: "none"
        }
      });
      doms.hiddenDiv.setAttribute(cola.constants.IGNORE_DIRECTIVE, "");
      document.body.appendChild(doms.hiddenDiv);
    }
    doms.hiddenDiv.appendChild(ele);
    cola._ignoreNodeRemoved = false;
  };

  USER_DATA_KEY = cola.constants.DOM_USER_DATA_KEY;

  cola.util.userDataStore = {};

  cola.util.userData = function(node, key, data) {
    var i, id, store, text, userData;
    if (node.nodeType === 3) {
      return;
    }
    userData = cola.util.userDataStore;
    if (node.nodeType === 8) {
      text = node.nodeValue;
      i = text.indexOf("|");
      if (i > -1) {
        id = text.substring(i + 1);
      }
    } else {
      id = node.getAttribute(USER_DATA_KEY);
    }
    if (arguments.length === 3) {
      if (!id) {
        id = cola.uniqueId();
        if (node.nodeType === 8) {
          if (i > -1) {
            node.nodeValue = text.substring(0, i + 1) + id;
          } else {
            node.nodeValue = text ? text + "|" + id : "|" + id;
          }
        } else {
          node.setAttribute(USER_DATA_KEY, id);
        }
        userData[id] = store = {};
      } else {
        store = userData[id];
        if (!store) {
          userData[id] = store = {};
        }
      }
      store[key] = data;
    } else if (arguments.length === 2) {
      if (typeof key === "string") {
        if (id) {
          store = userData[id];
          return store != null ? store[key] : void 0;
        }
      } else if (key && typeof key === "object") {
        id = cola.uniqueId();
        if (node.nodeType === 8) {
          if (i > -1) {
            node.nodeValue = text.substring(0, i + 1) + id;
          } else {
            node.nodeValue = text ? text + "|" + id : "|" + id;
          }
        } else {
          node.setAttribute(USER_DATA_KEY, id);
        }
        userData[id] = key;
      }
    } else if (arguments.length === 1) {
      if (id) {
        return userData[id];
      }
    }
  };

  cola.util.removeUserData = function(node, key) {
    var id, store;
    id = node.getAttribute(USER_DATA_KEY);
    if (id) {
      store = cola.util.userDataStore[id];
      if (store) {
        value = store[key];
        delete store[key];
      }
    }
    return value;
  };

  ON_NODE_REMOVED_KEY = "__onNodeRemoved";

  cola.detachNode = function(node) {
    if (!node.parentNode) {
      return;
    }
    cola._ignoreNodeRemoved = true;
    node.parentNode.removeChild(node);
    cola._ignoreNodeRemoved = false;
  };

  cola.util.onNodeDispose = function(node, listener) {
    var oldListener;
    oldListener = cola.util.userData(node, ON_NODE_REMOVED_KEY);
    if (oldListener) {
      if (oldListener instanceof Array) {
        oldListener.push(listener);
      } else {
        cola.util.userData(node, ON_NODE_REMOVED_KEY, [oldListener, listener]);
      }
    } else {
      cola.util.userData(node, ON_NODE_REMOVED_KEY, listener);
    }
  };

  _nodesToBeRemove = {};

  setInterval(function() {
    var changed, id, l, len1, listener, node, nodeRemovedListener, store;
    for (id in _nodesToBeRemove) {
      node = _nodesToBeRemove[id];
      store = cola.util.userDataStore[id];
      if (store) {
        changed = true;
        nodeRemovedListener = store[ON_NODE_REMOVED_KEY];
        if (nodeRemovedListener) {
          if (nodeRemovedListener instanceof Array) {
            for (l = 0, len1 = nodeRemovedListener.length; l < len1; l++) {
              listener = nodeRemovedListener[l];
              listener(node, store);
            }
          } else {
            nodeRemovedListener(node, store);
          }
        }
        delete cola.util.userDataStore[id];
      }
    }
    if (changed) {
      _nodesToBeRemove = {};
    }
  }, 10000);

  _getNodeDataId = function(node) {
    var i, id, text;
    if (node.nodeType === 3) {
      return;
    }
    if (node.nodeType === 8) {
      text = node.nodeValue;
      i = text.indexOf("|");
      if (i > -1) {
        id = text.substring(i + 1);
      }
    } else {
      id = node.getAttribute(USER_DATA_KEY);
    }
    return id;
  };

  _DOMNodeInsertedListener = function(evt) {
    var child, id, node;
    node = evt.target;
    if (!node) {
      return;
    }
    child = node.firstChild;
    while (child) {
      id = _getNodeDataId(child);
      if (id) {
        delete _nodesToBeRemove[id];
      }
      child = child.nextSibling;
    }
    id = _getNodeDataId(node);
    if (id) {
      delete _nodesToBeRemove[id];
    }
  };

  _DOMNodeRemovedListener = function(evt) {
    var child, id, node;
    if (cola._ignoreNodeRemoved || window.closed) {
      return;
    }
    node = evt.target;
    if (!node) {
      return;
    }
    child = node.firstChild;
    while (child) {
      id = _getNodeDataId(child);
      if (id) {
        _nodesToBeRemove[id] = child;
      }
      child = child.nextSibling;
    }
    id = _getNodeDataId(node);
    if (id) {
      _nodesToBeRemove[id] = node;
    }
  };

  document.addEventListener("DOMNodeInserted", _DOMNodeInsertedListener);

  document.addEventListener("DOMNodeRemoved", _DOMNodeRemovedListener);

  $fly(window).on("unload", function() {
    document.removeEventListener("DOMNodeInserted", _DOMNodeInsertedListener);
    document.removeEventListener("DOMNodeRemoved", _DOMNodeRemovedListener);
  });

  if (cola.device.mobile) {
    $fly(window).on("load", function() {
      FastClick.attach(document.body);
    });
  }

  if (cola.browser.webkit) {
    browser = "webkit";
    if (cola.browser.chrome) {
      browser += " chrome";
    } else if (cola.browser.safari) {
      browser += " safari";
    }
  } else if (cola.browser.ie) {
    browser = "ie";
  } else if (cola.browser.mozilla) {
    browser = "mozilla";
  } else {
    browser = "";
  }

  if (cola.os.android) {
    os = " android";
  } else if (cola.os.ios) {
    os = " ios";
  } else if (cola.os.windows) {
    os = " windows";
  } else {
    os = "";
  }

  if (cola.device.mobile) {
    os += " mobile";
  } else if (cola.device.desktop) {
    os += " desktop";
  }

  if (browser || os) {
    $fly(document.documentElement).addClass(browser + os);
  }

  if (cola.os.mobile) {
    $(function() {
      if (typeof FastClick !== "undefined" && FastClick !== null) {
        FastClick.attach(document.body);
      }
    });
  }

  cola.loadSubView = function(targetDom, context) {
    var cssUrl, cssUrls, failed, htmlUrl, jsUrl, jsUrls, l, len1, len2, len3, len4, loadingUrls, o, q, ref, ref1, resourceLoadCallback, u;
    loadingUrls = [];
    failed = false;
    resourceLoadCallback = function(success, result, url) {
      var error, errorMessage, hasIgnoreDirective, i, initFunc, l, len1, ref;
      if (success) {
        if (!failed) {
          i = loadingUrls.indexOf(url);
          if (i > -1) {
            loadingUrls.splice(i, 1);
          }
          if (loadingUrls.length === 0) {
            $fly(targetDom).removeClass("loading");
            if (targetDom.hasAttribute(cola.constants.IGNORE_DIRECTIVE)) {
              hasIgnoreDirective = true;
              targetDom.removeAttribute(cola.constants.IGNORE_DIRECTIVE);
            }
            if (context.suspendedInitFuncs.length) {
              ref = context.suspendedInitFuncs;
              for (l = 0, len1 = ref.length; l < len1; l++) {
                initFunc = ref[l];
                initFunc(targetDom, context.model, context.param);
              }
            } else {
              cola(targetDom, context.model);
            }
            if (hasIgnoreDirective) {
              targetDom.setAttribute(cola.constants.IGNORE_DIRECTIVE, true);
            }
            if (cola.getListeners("ready")) {
              cola.fire("ready", cola);
              cola.off("ready");
            }
            cola.callback(context.callback, true);
          }
        }
      } else {
        failed = true;
        error = result;
        if (cola.callback(context.callback, false, error) !== false) {
          if (error.xhr) {
            errorMessage = error.status + " " + error.statusText;
          } else {
            errorMessage = error.message;
          }
          throw new cola.Exception(("Failed to load resource from [" + url + "]. ") + errorMessage);
        }
      }
    };
    $fly(targetDom).addClass("loading");
    htmlUrl = context.htmlUrl;
    if (htmlUrl) {
      loadingUrls.push(htmlUrl);
    }
    if (context.jsUrl) {
      jsUrls = [];
      if (context.jsUrl instanceof Array) {
        ref = context.jsUrl;
        for (l = 0, len1 = ref.length; l < len1; l++) {
          jsUrl = ref[l];
          jsUrl = _compileResourceUrl(jsUrl, htmlUrl, ".js");
          if (jsUrl) {
            loadingUrls.push(jsUrl);
            jsUrls.push(jsUrl);
          }
        }
      } else {
        jsUrl = _compileResourceUrl(context.jsUrl, htmlUrl, ".js");
        if (jsUrl) {
          loadingUrls.push(jsUrl);
          jsUrls.push(jsUrl);
        }
      }
    }
    if (context.cssUrl) {
      cssUrls = [];
      if (context.cssUrl instanceof Array) {
        ref1 = context.cssUrl;
        for (o = 0, len2 = ref1.length; o < len2; o++) {
          cssUrl = ref1[o];
          cssUrl = _compileResourceUrl(cssUrl, htmlUrl, ".css");
          if (cssUrl) {
            loadingUrls.push(cssUrl);
            cssUrls.push(cssUrl);
          }
        }
      } else {
        cssUrl = _compileResourceUrl(context.cssUrl, htmlUrl, ".css");
        if (cssUrl) {
          loadingUrls.push(cssUrl);
          cssUrls.push(cssUrl);
        }
      }
    }
    context.suspendedInitFuncs = [];
    if (htmlUrl) {
      _loadHtml(targetDom, htmlUrl, void 0, {
        complete: function(success, result) {
          return resourceLoadCallback(success, result, htmlUrl);
        }
      });
    }
    if (jsUrls) {
      for (q = 0, len3 = jsUrls.length; q < len3; q++) {
        jsUrl = jsUrls[q];
        _loadJs(context, jsUrl, {
          complete: function(success, result) {
            return resourceLoadCallback(success, result, jsUrl);
          }
        });
      }
    }
    if (cssUrls) {
      for (u = 0, len4 = cssUrls.length; u < len4; u++) {
        cssUrl = cssUrls[u];
        _loadCss(cssUrl, {
          complete: function(success, result) {
            return resourceLoadCallback(success, result, cssUrl);
          }
        });
      }
    }
  };

  cola.unloadSubView = function(targetDom, context) {
    var cssUrl, htmlUrl, l, len1, ref;
    $fly(targetDom).empty();
    htmlUrl = context.htmlUrl;
    if (context.cssUrl) {
      if (context.cssUrl instanceof Array) {
        ref = context.cssUrl;
        for (l = 0, len1 = ref.length; l < len1; l++) {
          cssUrl = ref[l];
          cssUrl = _compileResourceUrl(cssUrl, htmlUrl, ".css");
          if (cssUrl) {
            _unloadCss(cssUrl);
          }
        }
      } else {
        cssUrl = _compileResourceUrl(context.cssUrl, htmlUrl, ".css");
        if (cssUrl) {
          _unloadCss(cssUrl);
        }
      }
    }
  };

  _compileResourceUrl = function(resUrl, htmlUrl, suffix) {
    var defaultRes, i;
    if (resUrl === "$") {
      defaultRes = true;
    } else if (resUrl.indexOf("$.") === 0) {
      defaultRes = true;
      suffix = resUrl.substring(2);
    }
    if (defaultRes) {
      resUrl = null;
      if (htmlUrl) {
        i = htmlUrl.indexOf("?");
        if (i > 0) {
          htmlUrl = htmlUrl.substring(0, i);
        }
        i = htmlUrl.lastIndexOf(".");
        resUrl = (i > 0 ? htmlUrl.substring(0, i) : htmlUrl) + suffix;
      }
    }
    return resUrl;
  };

  _loadHtml = function(targetDom, url, data, callback) {
    $(targetDom).load(url, data, function(response, status, xhr) {
      if (status === "error") {
        cola.callback(callback, false, {
          xhr: xhr,
          status: xhr.status,
          statusText: xhr.statusText
        });
      } else {
        cola.callback(callback, true);
      }
    });
  };

  _jsCache = {};

  _loadJs = function(context, url, callback) {
    var initFuncs;
    initFuncs = _jsCache[url];
    if (initFuncs) {
      Array.prototype.push.apply(context.suspendedInitFuncs, initFuncs);
      cola.callback(callback, true);
    } else {
      $.ajax(url, {
        dataType: "text",
        cache: true
      }).done(function(script) {
        var e, head, scriptElement;
        scriptElement = $.xCreate({
          tagName: "script",
          language: "javascript",
          type: "text/javascript",
          charset: cola.setting("defaultCharset")
        });
        scriptElement.text = script;
        cola._suspendedInitFuncs = context.suspendedInitFuncs;
        try {
          try {
            head = document.querySelector("head") || document.documentElement;
            head.appendChild(scriptElement);
          } finally {
            delete cola._suspendedInitFuncs;
            _jsCache[url] = context.suspendedInitFuncs;
          }
          cola.callback(callback, true);
        } catch (_error) {
          e = _error;
          cola.callback(callback, false, e);
        }
      }).fail(function(xhr) {
        cola.callback(callback, false, {
          xhr: xhr,
          status: xhr.status,
          statusText: xhr.statusText
        });
      });
    }
  };

  _cssCache = {};

  _loadCss = function(url, callback) {
    var head, linkElement, refNum;
    linkElement = _cssCache[url];
    if (!linkElement) {
      linkElement = $.xCreate({
        tagName: "link",
        rel: "stylesheet",
        type: "text/css",
        charset: cola.setting("defaultCharset"),
        href: url
      });
      if (!(cola.os.android && cola.os.version < 4.4)) {
        $(linkElement).one("load", function() {
          cola.callback(callback, true);
        }).on("readystatechange", function(evt) {
          var ref;
          if (((ref = evt.target) != null ? ref.readyState : void 0) === "complete") {
            cola.callback(callback, true);
            $fly(this).off("readystatechange");
          }
        }).one("error", function() {
          cola.callback(callback, false);
        });
      }
      head = document.querySelector("head") || document.documentElement;
      linkElement.setAttribute("_refNum", "1");
      head.appendChild(linkElement);
      _cssCache[url] = linkElement;
      if (cola.os.android && cola.os.version < 4.4) {
        cola.callback(callback, true);
      }
      return true;
    } else {
      refNum = parseInt(linkElement.getAttribute("_refNum")) || 1;
      linkElement.setAttribute("_refNum", (refNum + 1) + "");
      cola.callback(callback, true);
      return false;
    }
  };

  _unloadCss = function(url) {
    var linkElement, refNum;
    linkElement = _cssCache[url];
    if (linkElement) {
      refNum = parseInt(linkElement.getAttribute("_refNum")) || 1;
      if (refNum > 1) {
        linkElement.setAttribute("_refNum", (refNum - 1) + "");
      } else {
        delete _cssCache[url];
        $fly(linkElement).remove();
      }
    }
  };

  routerRegistry = null;

  currentRoutePath = null;

  currentRouter = null;

  trimPath = function(path) {
    if (path) {
      if (path.charCodeAt(0) !== 47) {
        path = "/" + path;
      }
      if (path.charCodeAt(path.length - 1) === 47) {
        path = path.substring(0, path.length - 1);
      }
    }
    return path || "";
  };

  ignoreRouterSettingChange = false;

  cola.on("settingChange", function(self, arg) {
    var path, tPath;
    if (ignoreRouterSettingChange) {
      return;
    }
    if (arg.key === "routerContextPath" || arg.key === "defaultRouterPath") {
      path = cola.setting(arg.key);
      tPath = trimPath(path);
      if (tPath !== path) {
        ignoreRouterSettingChange = true;
        cola.setting(arg.key, tPath);
        ignoreRouterSettingChange = false;
      }
    }
  });

  cola.route = function(path, router) {
    var hasVariable, i, l, len1, len2, name, nameParts, o, optional, part, parts, pathParts, ref, variable;
    if (routerRegistry == null) {
      routerRegistry = new cola.util.KeyedArray();
    }
    if (typeof router === "function") {
      router = {
        enter: router
      };
    }
    router.path = path = trimPath(path);
    path = path.slice(1);
    if (!router.name) {
      name = path || cola.constants.DEFAULT_PATH;
      parts = name.split(/[\/\-]/);
      nameParts = [];
      for (i = l = 0, len1 = parts.length; l < len1; i = ++l) {
        part = parts[i];
        if (!part || part.charCodeAt(0) === 58) {
          continue;
        }
        nameParts.push(nameParts.length > 0 ? cola.util.capitalize(part) : part);
      }
      router.name = nameParts.join("");
    }
    router.pathParts = pathParts = [];
    if (path) {
      hasVariable = false;
      ref = path.split("/");
      for (o = 0, len2 = ref.length; o < len2; o++) {
        part = ref[o];
        if (part.charCodeAt(0) === 58) {
          optional = part.charCodeAt(part.length - 1) === 63;
          if (optional) {
            variable = part.substring(1, part.length - 1);
          } else {
            variable = part.substring(1);
          }
          hasVariable = true;
          pathParts.push({
            variable: variable,
            optional: optional
          });
        } else {
          pathParts.push(part);
        }
      }
      router.hasVariable = hasVariable;
    }
    routerRegistry.add(router.path, router);
    return router;
  };

  cola.getCurrentRoutePath = function() {
    return currentRoutePath;
  };

  cola.getCurrentRouter = function() {
    return currentRouter;
  };

  cola.setRoutePath = function(path, replace, alwaysNotify) {
    var i, pathRoot, pathname, realPath, routerMode;
    if (path && path.charCodeAt(0) === 35) {
      routerMode = "hash";
      path = path.substring(1);
    } else {
      routerMode = cola.setting("routerMode") || "hash";
    }
    if (routerMode === "hash") {
      if (path.charCodeAt(0) !== 47) {
        path = "/" + path;
      }
      if (window.location.hash !== path) {
        window.location.hash = path;
      }
      if (alwaysNotify) {
        _onHashChange();
      }
    } else {
      pathRoot = cola.setting("routerContextPath");
      if (pathRoot && path.charCodeAt(0) === 47) {
        realPath = cola.util.concatUrl(pathRoot, path);
      } else {
        realPath = path;
      }
      pathname = realPath;
      i = pathname.indexOf("?");
      if (i >= 0) {
        pathname = pathname.substring(0, i);
      }
      i = pathname.indexOf("#");
      if (i >= 0) {
        pathname = pathname.substring(0, i);
      }
      if (location.pathname !== pathname) {
        if (replace) {
          window.history.replaceState({
            path: realPath
          }, null, realPath);
        } else {
          window.history.pushState({
            path: realPath
          }, null, realPath);
        }
        if (location.pathname !== pathname) {
          realPath = location.pathname + location.search + location.hash;
          if (pathRoot && realPath.indexOf(pathRoot) === 0) {
            path = realPath.substring(pathRoot.length);
          }
          window.history.replaceState({
            path: realPath,
            originPath: path
          }, null, realPath);
        }
        _onStateChange(path);
      } else if (alwaysNotify) {
        _onStateChange(pathname);
      }
    }
  };

  _findRouter = function(path) {
    var defPart, defPathParts, gap, i, l, len1, len2, matching, o, param, pathParts, ref, router;
    if (!routerRegistry) {
      return null;
    }
    if (path == null) {
      path = cola.setting("defaultRouterPath");
    }
    path = trimPath(path).slice(1);
    pathParts = path ? path.split(/[\/\?\#]/) : [];
    ref = routerRegistry.elements;
    for (l = 0, len1 = ref.length; l < len1; l++) {
      router = ref[l];
      defPathParts = router.pathParts;
      gap = defPathParts.length - pathParts.length;
      if (!(gap === 0 || gap === 1 && defPathParts.length > 0 && defPathParts[defPathParts.length - 1].optional)) {
        continue;
      }
      matching = true;
      param = {};
      for (i = o = 0, len2 = defPathParts.length; o < len2; i = ++o) {
        defPart = defPathParts[i];
        if (typeof defPart === "string") {
          if (defPart !== pathParts[i]) {
            matching = false;
            break;
          }
        } else {
          if (i >= pathParts.length && !defPart.optional) {
            matching = false;
            break;
          }
          param[defPart.variable] = pathParts[i];
        }
      }
      if (matching) {
        break;
      }
    }
    if (matching) {
      router.param = param;
      return router;
    } else {
      return null;
    }
  };

  cola.createRouterModel = function(router) {
    var parentModel, parentModelName;
    if (router.parentModel instanceof cola.Scope) {
      parentModel = router.parentModel;
    } else {
      parentModelName = router.parentModel || cola.constants.DEFAULT_PATH;
      parentModel = cola.model(parentModelName);
    }
    if (!parentModel) {
      throw new cola.Exception("Parent Model \"" + parentModelName + "\" is undefined.");
    }
    return new cola.Model(router.name, parentModel);
  };

  _switchRouter = function(router, path) {
    var eventArg, model, oldModel, target;
    if (router.redirectTo) {
      cola.setRoutePath(router.redirectTo);
      return;
    }
    eventArg = {
      path: path,
      prev: currentRouter,
      next: router
    };
    if (cola.fire("beforeRouterSwitch", cola, eventArg) === false) {
      return;
    }
    if (currentRouter) {
      if (typeof currentRouter.leave === "function") {
        currentRouter.leave(currentRouter);
      }
      if (currentRouter.targetDom) {
        cola.unloadSubView(currentRouter.targetDom, {
          cssUrl: currentRouter.cssUrl
        });
        oldModel = cola.util.removeUserData(currentRouter.targetDom, "_model");
        if (oldModel != null) {
          oldModel.destroy();
        }
      }
    }
    if (router.templateUrl) {
      if (router.target) {
        if (router.target.nodeType) {
          target = router.target;
        } else {
          target = $(router.target)[0];
        }
      }
      if (!target) {
        target = document.getElementsByClassName(cola.constants.VIEW_PORT_CLASS)[0];
        if (!target) {
          target = document.getElementsByClassName(cola.constants.VIEW_CLASS)[0];
          if (!target) {
            target = document.body;
          }
        }
      }
      router.targetDom = target;
      $fly(target).empty();
    }
    currentRouter = router;
    if (router.templateUrl) {
      model = cola.createRouterModel(router);
      eventArg.nextModel = model;
      cola.util.userData(router.targetDom, "_model", model);
      cola.loadSubView(router.targetDom, {
        model: model,
        htmlUrl: router.templateUrl,
        jsUrl: router.jsUrl,
        cssUrl: router.cssUrl,
        data: router.data,
        param: router.param,
        callback: function() {
          if (typeof router.enter === "function") {
            router.enter(router, model);
          }
          if (router.title) {
            document.title = router.title;
          }
        }
      });
    } else {
      if (typeof router.enter === "function") {
        router.enter(router, null);
      }
      if (router.title) {
        document.title = router.title;
      }
    }
    cola.fire("routerSwitch", cola, eventArg);
  };

  _getHashPath = function() {
    var path;
    path = location.hash;
    if (path) {
      path = path.substring(1);
    }
    if ((path != null ? path.charCodeAt(0) : void 0) === 33) {
      path = path.substring(1);
    }
    return trimPath(path);
  };

  _onHashChange = function() {
    var path, router;
    if ((cola.setting("routerMode") || "hash") !== "hash") {
      return;
    }
    path = _getHashPath();
    if (path === currentRoutePath) {
      return;
    }
    currentRoutePath = path;
    router = _findRouter(path);
    if (router) {
      _switchRouter(router, path);
    }
  };

  _onStateChange = function(path) {
    var i, router, routerContextPath;
    if (cola.setting("routerMode") !== "state") {
      return;
    }
    path = trimPath(path);
    i = path.indexOf("#");
    if (i > -1) {
      path = path.substring(i + 1);
    } else {
      i = path.indexOf("?");
      if (i > -1) {
        path = path.substring(0, i);
      }
    }
    if (path.charCodeAt(0) === 47) {
      routerContextPath = cola.setting("routerContextPath");
      if (routerContextPath && path.indexOf(routerContextPath) === 0) {
        path = path.slice(routerContextPath.length);
      }
    }
    if (path === currentRoutePath) {
      return;
    }
    currentRoutePath = path;
    router = _findRouter(path);
    if (router) {
      _switchRouter(router, path);
    }
  };

  $(function() {
    setTimeout(function() {
      var path, router;
      $fly(window).on("hashchange", _onHashChange).on("popstate", function() {
        var state;
        if (!location.hash) {
          state = window.history.state;
          _onStateChange((state != null ? state.path : void 0) || "/");
        }
      });
      $(document.body).delegate("a.state", "click", function() {
        var href, target;
        href = this.getAttribute("href");
        if (href) {
          target = this.getAttribute("target");
          if (!target || target === "_self") {
            cola.setRoutePath(href);
            return false;
          }
        }
      });
      path = _getHashPath();
      if (path) {
        router = _findRouter(path);
        if (router) {
          _switchRouter(router, path);
        }
      } else {
        path = cola.setting("defaultRouterPath");
        router = _findRouter(path);
        if (router) {
          cola.setRoutePath(path + location.search, true, true);
        }
      }
    }, 0);
  });


  /*
  BindingFeature
   */

  cola._BindingFeature = (function() {
    function _BindingFeature() {}

    _BindingFeature.prototype.init = function() {};

    return _BindingFeature;

  })();

  cola._ExpressionFeature = (function(superClass) {
    extend(_ExpressionFeature, superClass);

    function _ExpressionFeature(expression1) {
      this.expression = expression1;
      if (this.expression) {
        this.isStatic = this.expression.isStatic;
        this.isDyna = this.expression.isDyna;
        this.paths = this.expression.paths;
        if (!this.paths && this.expression.hasCallStatement) {
          this.paths = ["**"];
          if (!this.isStatic) {
            this.delay = true;
          }
        }
        this.watchingMoreMessage = this.expression.hasCallStatement;
      }
    }

    _ExpressionFeature.prototype.evaluate = function(domBinding, dynaExpressionOnly, dataCtx, loadMode) {
      var l, len1, len2, o, path, paths, ref, ref1, result;
      if (loadMode == null) {
        loadMode = "async";
      }
      if (dataCtx == null) {
        dataCtx = {};
      }
      if (dataCtx.vars == null) {
        dataCtx.vars = {};
      }
      dataCtx.vars.$dom = domBinding.dom;
      if (dynaExpressionOnly) {
        result = (ref = this.dynaExpression) != null ? ref.evaluate(domBinding.scope, loadMode, dataCtx) : void 0;
      } else {
        result = this.expression.evaluate(domBinding.scope, loadMode, dataCtx);
        if (this.isDyna && result !== this.dynaExpressionStr) {
          this.dynaExpressionStr = result;
          if (!this.ignoreBind && this.dynaPaths) {
            ref1 = this.dynaPaths;
            for (l = 0, len1 = ref1.length; l < len1; l++) {
              path = ref1[l];
              domBinding.unbind(path, this);
            }
          }
          if (typeof result === "string") {
            this.dynaExpression = cola._compileExpression(result);
            if (this.dynaExpression) {
              if (!this.ignoreBind) {
                paths = this.dynaExpression.paths;
                if (paths) {
                  for (o = 0, len2 = paths.length; o < len2; o++) {
                    path = paths[o];
                    if (this.paths.indexOf(path) < 0) {
                      if (!this.dynaPaths) {
                        this.dynaPaths = [path];
                      } else {
                        this.dynaPaths.push(path);
                      }
                      domBinding.bind(path, this);
                    }
                  }
                }
              }
              result = this.dynaExpression.evaluate(domBinding.scope, loadMode, dataCtx);
            }
          }
        }
      }
      return result;
    };

    _ExpressionFeature.prototype.refresh = function(domBinding, force, dynaExpressionOnly, dataCtx) {
      if (dataCtx == null) {
        dataCtx = {};
      }
      if (!this._refresh) {
        return;
      }
      if (this.delay && !force) {
        cola.util.delay(domBinding, "refresh", 100, (function(_this) {
          return function() {
            _this._refresh(domBinding, dynaExpressionOnly, dataCtx);
            if (_this.isStatic && !dataCtx.unloaded) {
              _this.disabled = true;
            }
          };
        })(this));
      } else {
        this._refresh(domBinding, dynaExpressionOnly, dataCtx);
        if (this.isStatic && !dataCtx.unloaded) {
          this.disabled = true;
        }
      }
    };

    return _ExpressionFeature;

  })(cola._BindingFeature);

  cola._WatchFeature = (function(superClass) {
    extend(_WatchFeature, superClass);

    function _WatchFeature(action1, paths1) {
      this.action = action1;
      this.paths = paths1;
      this.watchingMoreMessage = true;
    }

    _WatchFeature.prototype._processMessage = function(domBinding, bindingPath) {
      var ref;
      if (!this.isDyna || ((ref = this.dynaPaths) != null ? ref.indexOf(bindingPath) : void 0) >= 0) {
        this.refresh(domBinding);
      }
    };

    _WatchFeature.prototype.refresh = function(domBinding) {
      var action;
      action = domBinding.scope.action(this.action);
      if (!action) {
        throw new cola.Exception("No action named \"" + this.action + "\" found.");
      }
      action(domBinding.dom, domBinding.scope);
    };

    return _WatchFeature;

  })(cola._BindingFeature);

  cola._EventFeature = (function(superClass) {
    extend(_EventFeature, superClass);

    _EventFeature.prototype.ignoreBind = true;

    function _EventFeature(expression1, event) {
      this.expression = expression1;
      this.event = event;
    }

    _EventFeature.prototype.init = function(domBinding) {
      domBinding.$dom.on(this.event, (function(_this) {
        return function(evt) {
          var oldScope;
          oldScope = cola.currentScope;
          cola.currentScope = domBinding.scope;
          try {
            return _this.evaluate(domBinding, false, {
              vars: {
                $event: evt
              }
            }, "never");
          } finally {
            cola.currentScope = oldScope;
          }
        };
      })(this));
    };

    return _EventFeature;

  })(cola._ExpressionFeature);

  cola._AliasFeature = (function(superClass) {
    extend(_AliasFeature, superClass);

    function _AliasFeature(expression) {
      _AliasFeature.__super__.constructor.call(this, expression);
      this.alias = expression.alias;
    }

    _AliasFeature.prototype.init = function(domBinding) {
      domBinding.scope = new cola.AliasScope(domBinding.scope, this.expression);
      domBinding.subScopeCreated = true;
    };

    _AliasFeature.prototype._processMessage = function(domBinding, bindingPath, path, type, arg) {
      var ref;
      if ((cola.constants.MESSAGE_REFRESH <= type && type <= cola.constants.MESSAGE_CURRENT_CHANGE) || this.watchingMoreMessage) {
        this.refresh(domBinding, false, ((ref = this.dynaPaths) != null ? ref.indexOf(bindingPath) : void 0) >= 0);
      }
    };

    _AliasFeature.prototype._refresh = function(domBinding, dynaExpressionOnly, dataCtx) {
      var data;
      data = this.evaluate(domBinding, dynaExpressionOnly, dataCtx);
      domBinding.scope.data.setTargetData(data);
    };

    return _AliasFeature;

  })(cola._ExpressionFeature);

  cola._RepeatFeature = (function(superClass) {
    extend(_RepeatFeature, superClass);

    function _RepeatFeature(expression) {
      _RepeatFeature.__super__.constructor.call(this, expression);
      this.alias = expression.alias;
    }

    _RepeatFeature.prototype.init = function(domBinding) {
      var scope;
      domBinding.scope = scope = new cola.ItemsScope(domBinding.scope, this.isDyna ? null : this.expression);
      scope.onItemsRefresh = (function(_this) {
        return function() {
          _this.onItemsRefresh(domBinding);
        };
      })(this);
      scope.onCurrentItemChange = function(arg) {
        var currentItemDom, currentItemDomBinding, itemId;
        if (domBinding.currentItemDom) {
          $fly(domBinding.currentItemDom).removeClass(cola.constants.COLLECTION_CURRENT_CLASS);
        }
        if (arg.current && domBinding.itemDomBindingMap) {
          itemId = cola.Entity._getEntityId(arg.current);
          if (itemId) {
            currentItemDomBinding = domBinding.itemDomBindingMap[itemId];
            if (currentItemDomBinding) {
              currentItemDom = currentItemDomBinding.dom;
              $fly(currentItemDom).addClass(cola.constants.COLLECTION_CURRENT_CLASS);
            } else {
              this.onItemsRefresh(domBinding);
              return;
            }
          }
        }
        domBinding.currentItemDom = currentItemDom;
      };
      scope.onItemInsert = (function(_this) {
        return function(arg) {
          var entity, headDom, i, iScope, id, index, insertMode, itemDom, itemsScope, ref, ref1, refDom, refEntityId, refItemScope, tailDom, templateDom;
          headDom = domBinding.dom;
          tailDom = cola.util.userData(headDom, cola.constants.REPEAT_TAIL_KEY);
          templateDom = cola.util.userData(headDom, cola.constants.REPEAT_TEMPLATE_KEY);
          entity = arg.entity;
          itemsScope = arg.itemsScope;
          insertMode = arg.insertMode;
          if (!insertMode || insertMode === "end") {
            index = arg.entityList.entityCount;
          } else if (insertMode === "begin") {
            index = 1;
          } else if (insertMode === "before") {
            refItemScope = itemsScope.getItemScope(arg.refEntity);
            index = refItemScope != null ? refItemScope.data.getIndex() : void 0;
          } else if (insertMode === "after") {
            refItemScope = itemsScope.getItemScope(arg.refEntity);
            index = (refItemScope != null ? refItemScope.data.getIndex() : void 0) + 1;
          }
          itemDom = _this.createNewItem(domBinding, templateDom, domBinding.scope, entity, index);
          if (!insertMode || insertMode === "end") {
            $fly(tailDom).before(itemDom);
          } else {
            if (insertMode === "begin") {
              $fly(headDom).after(itemDom);
            } else if (domBinding.itemDomBindingMap) {
              refEntityId = cola.Entity._getEntityId(arg.refEntity);
              if (refEntityId) {
                refDom = (ref = domBinding.itemDomBindingMap[refEntityId]) != null ? ref.dom : void 0;
                if (refDom) {
                  if (insertMode === "before") {
                    $fly(refDom).before(itemDom);
                  } else {
                    $fly(refDom).after(itemDom);
                  }
                }
              }
            }
            ref1 = itemsScope.itemScopeMap;
            for (id in ref1) {
              iScope = ref1[id];
              i = iScope.data.getIndex();
              if (i >= index && iScope.data.getTargetData() !== entity) {
                iScope.data.setIndex(i + 1);
              }
            }
          }
        };
      })(this);
      scope.onItemRemove = function(arg) {
        var entity, i, iScope, id, index, itemDomBinding, itemId, itemScope, itemsScope, ref;
        entity = arg.entity;
        itemsScope = arg.itemsScope;
        itemId = cola.Entity._getEntityId(entity);
        if (itemId) {
          itemScope = itemsScope.getItemScope(entity);
          itemDomBinding = domBinding.itemDomBindingMap[itemId];
          if (itemDomBinding) {
            itemsScope.unregItemScope(itemId);
            itemDomBinding.remove();
            if (itemDomBinding.dom === domBinding.currentItemDom) {
              delete domBinding.currentItemDom;
            }
          }
          if (itemScope) {
            index = itemScope.data.getIndex();
            if (index < arg.entityList.entityCount) {
              ref = itemsScope.itemScopeMap;
              for (id in ref) {
                iScope = ref[id];
                i = iScope.data.getIndex();
                if (i > index) {
                  iScope.data.setIndex(i - 1);
                }
              }
            }
          }
        }
      };
      domBinding.subScopeCreated = true;
    };

    _RepeatFeature.prototype._processMessage = function(domBinding, bindingPath, path, type, arg) {
      if ((cola.constants.MESSAGE_REFRESH <= type && type <= cola.constants.MESSAGE_CURRENT_CHANGE) || this.watchingMoreMessage) {
        this.refresh(domBinding);
      }
    };

    _RepeatFeature.prototype._refresh = function(domBinding, dynaExpressionOnly, dataCtx) {
      if (this.isDyna && !dynaExpressionOnly) {
        this.evaluate(domBinding, dynaExpressionOnly, dataCtx);
        domBinding.scope.setExpression(this.dynaExpression);
      }
      domBinding.scope.refreshItems(dataCtx);
    };

    _RepeatFeature.prototype.onItemsRefresh = function(domBinding) {
      var currentDom, documentFragment, headDom, itemDom, items, originItems, scope, tailDom, templateDom;
      scope = domBinding.scope;
      items = scope.items;
      originItems = scope.originData;
      if (items && !(items instanceof cola.EntityList) && !(items instanceof Array)) {
        throw new cola.Exception("Expression \"" + this.expression + "\" must bind to EntityList or Array.");
      }
      if (items !== domBinding.items || (items && items.timestamp !== domBinding.timestamp)) {
        domBinding.items = items;
        domBinding.timestamp = (items != null ? items.timestamp : void 0) || 0;
        headDom = domBinding.dom;
        tailDom = cola.util.userData(headDom, cola.constants.REPEAT_TAIL_KEY);
        templateDom = cola.util.userData(headDom, cola.constants.REPEAT_TEMPLATE_KEY);
        if (!tailDom) {
          tailDom = document.createComment("Repeat Tail ");
          $fly(headDom).after(tailDom);
          cola.util.userData(headDom, cola.constants.REPEAT_TAIL_KEY, tailDom);
        }
        currentDom = headDom;
        documentFragment = null;
        if (items) {
          domBinding.itemDomBindingMap = {};
          scope.resetItemScopeMap();
          if (domBinding.currentItemDom) {
            $fly(domBinding.currentItemDom).removeClass(cola.constants.COLLECTION_CURRENT_CLASS);
          }
          cola.each(items, (function(_this) {
            return function(item, i) {
              var itemDom, itemDomBinding, itemId, itemScope;
              if (item == null) {
                return;
              }
              itemDom = currentDom.nextSibling;
              if (itemDom === tailDom) {
                itemDom = null;
              }
              if (itemDom) {
                itemDomBinding = cola.util.userData(itemDom, cola.constants.DOM_BINDING_KEY);
                itemScope = itemDomBinding.scope;
                if (typeof item === "object") {
                  itemId = cola.Entity._getEntityId(item);
                } else {
                  itemId = cola.uniqueId();
                }
                scope.regItemScope(itemId, itemScope);
                itemDomBinding.itemId = itemId;
                domBinding.itemDomBindingMap[itemId] = itemDomBinding;
                itemScope.data.setTargetData(item);
                itemScope.data.setIndex(i + 1);
              } else {
                itemDom = _this.createNewItem(domBinding, templateDom, scope, item, i + 1);
                if (documentFragment == null) {
                  documentFragment = document.createDocumentFragment();
                }
                documentFragment.appendChild(itemDom);
                $fly(tailDom).before(itemDom);
              }
              if (item === (items.current || (originItems != null ? originItems.current : void 0))) {
                $fly(itemDom).addClass(cola.constants.COLLECTION_CURRENT_CLASS);
                domBinding.currentItemDom = itemDom;
              }
              currentDom = itemDom;
            };
          })(this));
        }
        if (!documentFragment) {
          itemDom = currentDom.nextSibling;
          while (itemDom && itemDom !== tailDom) {
            currentDom = itemDom;
            itemDom = currentDom.nextSibling;
            $fly(currentDom).remove();
          }
        } else {
          $fly(tailDom).before(documentFragment);
        }
      }
    };

    _RepeatFeature.prototype.createNewItem = function(repeatDomBinding, templateDom, scope, item, index) {
      var domBinding, itemDom, itemId, itemScope, templateDomBinding;
      itemScope = new cola.ItemScope(scope, this.alias);
      itemScope.data.setTargetData(item, true);
      itemScope.data.setIndex(index, true);
      itemDom = templateDom.cloneNode(true);
      this.deepCloneNodeData(itemDom, itemScope, false);
      templateDomBinding = cola.util.userData(templateDom, cola.constants.DOM_BINDING_KEY);
      domBinding = templateDomBinding.clone(itemDom, itemScope);
      this.refreshItemDomBinding(itemDom, itemScope);
      if (typeof item === "object") {
        itemId = cola.Entity._getEntityId(item);
      } else {
        itemId = cola.uniqueId();
      }
      scope.regItemScope(itemId, itemScope);
      domBinding.itemId = itemId;
      repeatDomBinding.itemDomBindingMap[itemId] = domBinding;
      return itemDom;
    };

    _RepeatFeature.prototype.deepCloneNodeData = function(node, scope, cloneDomBinding) {
      var child, clonedStore, k, store, v;
      store = cola.util.userData(node);
      if (store) {
        clonedStore = {};
        for (k in store) {
          v = store[k];
          if (k === cola.constants.DOM_BINDING_KEY) {
            if (cloneDomBinding) {
              v = v.clone(node, scope);
            }
          } else if (k.substring(0, 2) === "__") {
            continue;
          }
          clonedStore[k] = v;
        }
        cola.util.userData(node, clonedStore);
      }
      child = node.firstChild;
      while (child) {
        if (child.nodeType !== 3 && !(typeof child.hasAttribute === "function" ? child.hasAttribute(cola.constants.IGNORE_DIRECTIVE) : void 0)) {
          this.deepCloneNodeData(child, scope, true);
        }
        child = child.nextSibling;
      }
    };

    _RepeatFeature.prototype.refreshItemDomBinding = function(dom, itemScope) {
      var child, currentDom, domBinding, initializer, initializers, l, len1;
      domBinding = cola.util.userData(dom, cola.constants.DOM_BINDING_KEY);
      if (domBinding) {
        domBinding.refresh();
        itemScope = domBinding.subScope || domBinding.scope;
        if (domBinding instanceof cola._RepeatDomBinding) {
          currentDom = cola.util.userData(domBinding.dom, cola.constants.REPEAT_TAIL_KEY);
        }
      }
      child = dom.firstChild;
      while (child) {
        if (child.nodeType !== 3 && !(typeof child.hasAttribute === "function" ? child.hasAttribute(cola.constants.IGNORE_DIRECTIVE) : void 0)) {
          child = this.refreshItemDomBinding(child, itemScope);
        }
        child = child.nextSibling;
      }
      initializers = cola.util.userData(dom, cola.constants.DOM_INITIALIZER_KEY);
      if (initializers) {
        for (l = 0, len1 = initializers.length; l < len1; l++) {
          initializer = initializers[l];
          initializer(itemScope, dom);
        }
        cola.util.removeUserData(dom, cola.constants.DOM_INITIALIZER_KEY);
      }
      return currentDom || dom;
    };

    return _RepeatFeature;

  })(cola._ExpressionFeature);

  cola._DomFeature = (function(superClass) {
    extend(_DomFeature, superClass);

    function _DomFeature() {
      return _DomFeature.__super__.constructor.apply(this, arguments);
    }

    _DomFeature.prototype.writeBack = function(domBinding, value) {
      var paths;
      paths = this.isDyna ? this.dynaPaths : this.paths;
      if (paths && paths.length === 1) {
        this.ignoreMessage = true;
        domBinding.scope.set(paths[0], value);
        this.ignoreMessage = false;
      }
    };

    _DomFeature.prototype._processMessage = function(domBinding, bindingPath, path, type, arg) {
      var ref;
      if ((cola.constants.MESSAGE_REFRESH <= type && type <= cola.constants.MESSAGE_CURRENT_CHANGE) || this.watchingMoreMessage) {
        this.refresh(domBinding, false, ((ref = this.dynaPaths) != null ? ref.indexOf(bindingPath) : void 0) >= 0);
      }
    };

    _DomFeature.prototype._refresh = function(domBinding, dynaExpressionOnly, dataCtx) {
      if (this.ignoreMessage) {
        return;
      }
      value = this.evaluate(domBinding, dynaExpressionOnly, dataCtx);
      this._doRender(domBinding, value);
    };

    return _DomFeature;

  })(cola._ExpressionFeature);

  cola._DomAttrFeature = (function(superClass) {
    extend(_DomAttrFeature, superClass);

    function _DomAttrFeature(expression, attr1) {
      this.attr = attr1;
      _DomAttrFeature.__super__.constructor.call(this, expression);
    }

    _DomAttrFeature.prototype._doRender = function(domBinding, value) {
      var attr, defaultDateFormat;
      if (value instanceof Date) {
        defaultDateFormat = cola.setting("defaultDateFormat");
        if (defaultDateFormat) {
          value = cola.defaultAction.formatDate(value, defaultDateFormat);
        }
      }
      attr = this.attr;
      if (attr === "text") {
        cola.util.setText(domBinding.dom, value != null ? value : "");
      } else if (attr === "html") {
        domBinding.$dom.html(value != null ? value : "");
      } else {
        domBinding.$dom.attr(attr, value != null ? value : "");
      }
    };

    return _DomAttrFeature;

  })(cola._DomFeature);

  cola._DomStylePropFeature = (function(superClass) {
    extend(_DomStylePropFeature, superClass);

    function _DomStylePropFeature(expression, prop1) {
      this.prop = prop1;
      _DomStylePropFeature.__super__.constructor.call(this, expression);
    }

    _DomStylePropFeature.prototype._doRender = function(domBinding, value) {
      domBinding.$dom.css(this.prop, value);
    };

    return _DomStylePropFeature;

  })(cola._DomFeature);

  cola._DomClassFeature = (function(superClass) {
    extend(_DomClassFeature, superClass);

    function _DomClassFeature(expression) {
      _DomClassFeature.__super__.constructor.call(this, expression);
    }

    _DomClassFeature.prototype._doRender = function(domBinding, value) {
      if (this._lastClassName) {
        domBinding.$dom.removeClass(this._lastClassName);
      }
      if (value) {
        domBinding.$dom.addClass(value);
        this._lastClassName = value;
      }
    };

    return _DomClassFeature;

  })(cola._DomFeature);

  cola._DomToggleClassFeature = (function(superClass) {
    extend(_DomToggleClassFeature, superClass);

    function _DomToggleClassFeature(expression, className1) {
      this.className = className1;
      _DomToggleClassFeature.__super__.constructor.call(this, expression);
    }

    _DomToggleClassFeature.prototype._doRender = function(domBinding, value) {
      domBinding.$dom[value ? "addClass" : "removeClass"](this.className);
    };

    return _DomToggleClassFeature;

  })(cola._DomFeature);

  cola._TextBoxFeature = (function(superClass) {
    extend(_TextBoxFeature, superClass);

    function _TextBoxFeature() {
      return _TextBoxFeature.__super__.constructor.apply(this, arguments);
    }

    _TextBoxFeature.prototype.init = function(domBinding) {
      var feature;
      feature = this;
      domBinding.$dom.on("input", function() {
        feature.writeBack(domBinding, this.value);
      });
      _TextBoxFeature.__super__.init.call(this);
    };

    _TextBoxFeature.prototype._doRender = function(domBinding, value) {
      domBinding.dom.value = value || "";
    };

    return _TextBoxFeature;

  })(cola._DomFeature);

  cola._CheckboxFeature = (function(superClass) {
    extend(_CheckboxFeature, superClass);

    function _CheckboxFeature() {
      return _CheckboxFeature.__super__.constructor.apply(this, arguments);
    }

    _CheckboxFeature.prototype.init = function(domBinding) {
      var feature;
      feature = this;
      domBinding.$dom.on("click", function() {
        feature.writeBack(domBinding, this.checked);
      });
      _CheckboxFeature.__super__.init.call(this);
    };

    _CheckboxFeature.prototype._doRender = function(domBinding, value) {
      var checked;
      checked = cola.DataType.defaultDataTypes.boolean.parse(value);
      domBinding.dom.checked = checked;
    };

    return _CheckboxFeature;

  })(cola._DomFeature);

  cola._RadioFeature = (function(superClass) {
    extend(_RadioFeature, superClass);

    function _RadioFeature() {
      return _RadioFeature.__super__.constructor.apply(this, arguments);
    }

    _RadioFeature.prototype.init = function(domBinding) {
      domBinding.$dom.on("click", function() {
        var checked;
        checked = this.checked;
        if (checked) {
          this.writeBack(domBinding, checked);
        }
      });
      _RadioFeature.__super__.init.call(this);
    };

    _RadioFeature.prototype._doRender = function(domBinding, value) {
      domBinding.dom.checked = value === domBinding.dom.value;
    };

    return _RadioFeature;

  })(cola._DomFeature);

  cola._SelectFeature = (function(superClass) {
    extend(_SelectFeature, superClass);

    function _SelectFeature() {
      return _SelectFeature.__super__.constructor.apply(this, arguments);
    }

    _SelectFeature.prototype.init = function(domBinding) {
      var feature;
      feature = this;
      domBinding.$dom.on("change", function() {
        value = this.options[this.selectedIndex];
        feature.writeBack(domBinding, value != null ? value.value : void 0);
      });
      _SelectFeature.__super__.init.call(this);
    };

    _SelectFeature.prototype._doRender = function(domBinding, value) {
      domBinding.dom.value = value;
    };

    return _SelectFeature;

  })(cola._DomFeature);

  cola._DisplayFeature = (function(superClass) {
    extend(_DisplayFeature, superClass);

    function _DisplayFeature() {
      return _DisplayFeature.__super__.constructor.apply(this, arguments);
    }

    _DisplayFeature.prototype._doRender = function(domBinding, value) {
      domBinding.dom.style.display = value ? "" : "none";
    };

    return _DisplayFeature;

  })(cola._DomFeature);

  cola._SelectOptionsFeature = (function(superClass) {
    extend(_SelectOptionsFeature, superClass);

    function _SelectOptionsFeature() {
      return _SelectOptionsFeature.__super__.constructor.apply(this, arguments);
    }

    _SelectOptionsFeature.prototype._doRender = function(domBinding, optionValues) {
      var options;
      if (!(optionValues instanceof Array || optionValues instanceof cola.EntityList)) {
        return;
      }
      options = domBinding.dom.options;
      if (optionValues instanceof cola.EntityList) {
        options.length = optionValues.entityCount;
      } else {
        options.length = optionValues.length;
      }
      cola.each(optionValues, function(optionValue, i) {
        var option;
        option = options[i];
        if (cola.util.isSimpleValue(optionValue)) {
          $fly(option).removeAttr("value").text(optionValue);
        } else if (optionValue instanceof cola.Entity) {
          $fly(option).attr("value", optionValue.get("value") || optionValue.get("key")).text(optionValue.get("text") || optionValue.get("name"));
        } else {
          $fly(option).attr("value", optionValue.value || optionValue.key).text(optionValue.text || optionValue.name);
        }
      });
    };

    return _SelectOptionsFeature;

  })(cola._DomFeature);

  _destroyDomBinding = function(node, data) {
    var domBinding;
    domBinding = data[cola.constants.DOM_BINDING_KEY];
    if (domBinding != null) {
      domBinding.destroy();
    }
  };

  cola._DomBinding = (function() {
    function _DomBinding(dom, scope1, features) {
      var f, l, len1;
      this.scope = scope1;
      this.dom = dom;
      this.$dom = $(dom);
      if (features) {
        for (l = 0, len1 = features.length; l < len1; l++) {
          f = features[l];
          this.addFeature(f);
        }
      }
      cola.util.userData(dom, cola.constants.DOM_BINDING_KEY, this);
      cola.util.onNodeDispose(dom, _destroyDomBinding);
    }

    _DomBinding.prototype.destroy = function() {
      var _features, i;
      _features = this.features;
      if (_features) {
        i = _features.length - 1;
        while (i >= 0) {
          this.unbindFeature(_features[i]);
          i--;
        }
      }
      delete this.dom;
      delete this.$dom;
    };

    _DomBinding.prototype.addFeature = function(feature) {
      if (feature.id == null) {
        feature.id = cola.uniqueId();
      }
      if (typeof feature.init === "function") {
        feature.init(this);
      }
      if (!this.features) {
        this.features = [feature];
      } else {
        this.features.push(feature);
      }
      if (!feature.ignoreBind) {
        this.bindFeature(feature);
      }
    };

    _DomBinding.prototype.removeFeature = function(feature) {
      var _features, i;
      _features = this.features;
      if (_features) {
        i = _features.indexOf(feature);
        if (i > -1) {
          _features.splice(i, 1);
        }
        if (!feature.ignoreBind) {
          this.unbindFeature(feature);
        }
      }
    };

    _DomBinding.prototype.bindFeature = function(feature) {
      var l, len1, path, paths;
      if (!feature._processMessage) {
        return;
      }
      paths = feature.paths;
      if (paths) {
        for (l = 0, len1 = paths.length; l < len1; l++) {
          path = paths[l];
          this.bind(path, feature);
        }
      }
    };

    _DomBinding.prototype.unbindFeature = function(feature) {
      var l, len1, path, paths;
      if (!feature._processMessage) {
        return;
      }
      paths = feature.paths;
      if (paths) {
        for (l = 0, len1 = paths.length; l < len1; l++) {
          path = paths[l];
          this.unbind(path, feature);
        }
      }
    };

    _DomBinding.prototype.bind = function(path, feature) {
      var holder, pipe;
      pipe = {
        path: path,
        _processMessage: (function(_this) {
          return function(bindingPath, path, type, arg) {
            if (!feature.disabled) {
              feature._processMessage(_this, bindingPath, path, type, arg);
              if (feature.disabled) {
                pipe.disabled = true;
              }
            } else {
              pipe.disabled = true;
            }
          };
        })(this)
      };
      this.scope.data.bind(path, pipe);
      holder = this[feature.id];
      if (!holder) {
        this[feature.id] = [pipe];
      } else {
        holder.push(pipe);
      }
    };

    _DomBinding.prototype.unbind = function(path, feature) {
      var holder, i, l, len1, p;
      holder = this[feature.id];
      for (i = l = 0, len1 = holder.length; l < len1; i = ++l) {
        p = holder[i];
        if (p.path === path) {
          this.scope.data.unbind(path, holder[i]);
          holder.splice(i, 1);
          break;
        }
      }
      if (!holder.length) {
        delete this[feature.id];
      }
    };

    _DomBinding.prototype.refresh = function(force) {
      var f, l, len1, ref;
      if (this.features) {
        ref = this.features;
        for (l = 0, len1 = ref.length; l < len1; l++) {
          f = ref[l];
          f.refresh(this, force);
        }
      }
    };

    _DomBinding.prototype.clone = function(dom, scope) {
      return new this.constructor(dom, scope, this.features, true);
    };

    return _DomBinding;

  })();

  cola._AliasDomBinding = (function(superClass) {
    extend(_AliasDomBinding, superClass);

    function _AliasDomBinding() {
      return _AliasDomBinding.__super__.constructor.apply(this, arguments);
    }

    _AliasDomBinding.prototype.destroy = function() {
      _AliasDomBinding.__super__.destroy.call(this);
      if (this.subScopeCreated) {
        this.scope.destroy();
      }
    };

    return _AliasDomBinding;

  })(cola._DomBinding);

  cola._RepeatDomBinding = (function(superClass) {
    extend(_RepeatDomBinding, superClass);

    function _RepeatDomBinding(dom, scope, feature, clone) {
      var f, headerNode, l, len1, repeatItemDomBinding;
      if (clone) {
        _RepeatDomBinding.__super__.constructor.call(this, dom, scope, feature);
      } else {
        this.scope = scope;
        headerNode = document.createComment("Repeat Head ");
        cola._ignoreNodeRemoved = true;
        dom.parentNode.replaceChild(headerNode, dom);
        cola.util.cacheDom(dom);
        cola._ignoreNodeRemoved = false;
        this.dom = headerNode;
        cola.util.userData(headerNode, cola.constants.DOM_BINDING_KEY, this);
        cola.util.userData(headerNode, cola.constants.REPEAT_TEMPLATE_KEY, dom);
        cola.util.onNodeDispose(headerNode, _destroyDomBinding);
        repeatItemDomBinding = new cola._RepeatItemDomBinding(dom, null);
        repeatItemDomBinding.repeatDomBinding = this;
        repeatItemDomBinding.isTemplate = true;
        if (feature) {
          if (feature instanceof Array) {
            for (l = 0, len1 = feature.length; l < len1; l++) {
              f = feature[l];
              if (f instanceof cola._RepeatFeature) {
                this.addFeature(f);
              } else {
                repeatItemDomBinding.addFeature(f);
              }
            }
          } else {
            if (feature instanceof cola._RepeatFeature) {
              this.addFeature(feature);
            } else {
              repeatItemDomBinding.addFeature(feature);
            }
          }
        }
      }
    }

    _RepeatDomBinding.prototype.destroy = function() {
      _RepeatDomBinding.__super__.destroy.call(this);
      if (this.subScopeCreated) {
        this.scope.destroy();
      }
      delete this.currentItemDom;
    };

    return _RepeatDomBinding;

  })(cola._DomBinding);

  cola._RepeatItemDomBinding = (function(superClass) {
    extend(_RepeatItemDomBinding, superClass);

    function _RepeatItemDomBinding() {
      return _RepeatItemDomBinding.__super__.constructor.apply(this, arguments);
    }

    _RepeatItemDomBinding.prototype.destroy = function() {
      var ref;
      _RepeatItemDomBinding.__super__.destroy.call(this);
      if (!this.isTemplate) {
        if ((ref = this.repeatDomBinding.itemDomBindingMap) != null) {
          delete ref[this.itemId];
        }
      }
    };

    _RepeatItemDomBinding.prototype.clone = function(dom, scope) {
      var cloned;
      cloned = _RepeatItemDomBinding.__super__.clone.call(this, dom, scope);
      cloned.repeatDomBinding = this.repeatDomBinding;
      return cloned;
    };

    _RepeatItemDomBinding.prototype.bind = function(path, feature) {
      if (this.isTemplate) {
        return;
      }
      return _RepeatItemDomBinding.__super__.bind.call(this, path, feature);
    };

    _RepeatItemDomBinding.prototype.bindFeature = function(feature) {
      if (this.isTemplate) {
        return;
      }
      return _RepeatItemDomBinding.__super__.bindFeature.call(this, feature);
    };

    _RepeatItemDomBinding.prototype.processDataMessage = function(path, type, arg) {
      if (!this.isTemplate) {
        this.scope.data._processMessage("**", path, type, arg);
      }
    };

    _RepeatItemDomBinding.prototype.refresh = function() {
      if (this.isTemplate) {
        return;
      }
      return _RepeatItemDomBinding.__super__.refresh.call(this);
    };

    _RepeatItemDomBinding.prototype.remove = function() {
      if (!this.isTemplate) {
        this.$dom.remove();
      }
    };

    return _RepeatItemDomBinding;

  })(cola._AliasDomBinding);

  IGNORE_NODES = ["SCRIPT", "STYLE", "META", "TEMPLATE"];

  ALIAS_REGEXP = new RegExp("\\$default", "g");

  cola._mainInitFuncs = [];

  cola._rootFunc = function() {
    var arg, fn, init, l, len1, model, modelName, targetDom;
    fn = null;
    targetDom = null;
    modelName = null;
    for (l = 0, len1 = arguments.length; l < len1; l++) {
      arg = arguments[l];
      if (typeof arg === "function") {
        fn = arg;
      } else if (typeof arg === "string") {
        modelName = arg;
      } else if (arg instanceof cola.Scope) {
        model = arg;
      } else if ((arg != null ? arg.nodeType : void 0) || typeof arg === "object" && arg.length > 0) {
        targetDom = arg;
      }
    }
    init = function(dom, model, param) {
      var len2, o, oldScope, viewDoms;
      oldScope = cola.currentScope;
      cola.currentScope = model;
      try {
        if (!model._dom) {
          model._dom = dom;
        } else {
          model._dom = model._dom.concat(dom);
        }
        delete model._$dom;
        if (typeof fn === "function") {
          fn(model, param);
        }
        if (!dom) {
          viewDoms = document.getElementsByClassName(cola.constants.VIEW_CLASS);
          if (viewDoms != null ? viewDoms.length : void 0) {
            dom = viewDoms;
          }
        }
        if (dom == null) {
          dom = document.body;
        }
        if (dom.length) {
          doms = dom;
          for (o = 0, len2 = doms.length; o < len2; o++) {
            dom = doms[o];
            cola._renderDomTemplate(dom, model);
          }
        } else {
          cola._renderDomTemplate(dom, model);
        }
      } finally {
        cola.currentScope = oldScope;
      }
    };
    if (cola._suspendedInitFuncs) {
      cola._suspendedInitFuncs.push(init);
    } else {
      if (!model) {
        if (modelName == null) {
          modelName = cola.constants.DEFAULT_PATH;
        }
        model = cola.model(modelName);
        if (model == null) {
          model = new cola.Model(modelName);
        }
      }
      if (cola._mainInitFuncs) {
        cola._mainInitFuncs.push({
          targetDom: targetDom,
          model: model,
          init: init
        });
      } else {
        init(targetDom, model);
      }
    }
    return cola;
  };

  $(function() {
    var initFunc, initFuncs, l, len1;
    initFuncs = cola._mainInitFuncs;
    delete cola._mainInitFuncs;
    for (l = 0, len1 = initFuncs.length; l < len1; l++) {
      initFunc = initFuncs[l];
      initFunc.init(initFunc.targetDom, initFunc.model);
    }
    if (cola.getListeners("ready")) {
      cola.fire("ready", cola);
      cola.off("ready");
    }
  });

  cola._userDomCompiler = {
    $: []
  };

  cola.xRender = function(template, model, context) {
    var child, div, documentFragment, dom, l, len1, len2, len3, next, node, o, oldScope, processor, q, ref, ref1, templateNode;
    if (!template) {
      return;
    }
    oldScope = cola.currentScope;
    model = model || oldScope;
    if (template.nodeType) {
      dom = template;
    } else if (typeof template === "string") {
      if (template.match(/^\#[\w\-\$]*$/)) {
        templateNode = document.getElementById(template.substring(1));
        if (templateNode) {
          if (template.nodeName === "TEMPLATE") {
            template = templateNode.innerHTML;
            $fly(templateNode).remove();
          }
        } else {
          template = null;
          dom = templateNode;
        }
      }
      if (template) {
        documentFragment = document.createDocumentFragment();
        div = document.createElement("div");
        div.innerHTML = template;
        child = div.firstChild;
        while (child) {
          next = child.nextSibling;
          documentFragment.appendChild(child);
          child = next;
        }
      }
    } else {
      cola.currentScope = model;
      try {
        if (context == null) {
          context = {};
        }
        if (template instanceof Array) {
          documentFragment = document.createDocumentFragment();
          for (l = 0, len1 = template.length; l < len1; l++) {
            node = template[l];
            child = null;
            ref = cola.xRender.nodeProcessors;
            for (o = 0, len2 = ref.length; o < len2; o++) {
              processor = ref[o];
              child = processor(node, context);
              if (child) {
                break;
              }
            }
            if (child == null) {
              child = $.xCreate(node, context);
            }
            if (child) {
              documentFragment.appendChild(child);
            }
          }
        } else {
          ref1 = cola.xRender.nodeProcessors;
          for (q = 0, len3 = ref1.length; q < len3; q++) {
            processor = ref1[q];
            dom = processor(template, context);
            if (dom) {
              break;
            }
          }
          if (!dom) {
            dom = $.xCreate(template, context);
          }
        }
      } finally {
        cola.currentScope = oldScope;
      }
    }
    if (dom) {
      cola._renderDomTemplate(dom, model, context);
    } else if (documentFragment) {
      cola._renderDomTemplate(documentFragment, model, context);
      if (documentFragment.firstChild === documentFragment.lastChild) {
        dom = documentFragment.firstChild;
      } else {
        dom = documentFragment;
      }
    }
    return dom;
  };

  cola.xRender.nodeProcessors = [];

  cola._renderDomTemplate = function(dom, scope, context) {
    if (context == null) {
      context = {};
    }
    _doRenderDomTemplate(dom, scope, context);
  };

  _doRenderDomTemplate = function(dom, scope, context) {
    var attr, attrName, attrValue, bindingExpr, bindingType, builder, child, childContext, customDomCompiler, defaultPath, domBinding, f, feature, features, initializer, initializers, k, l, len1, len2, len3, len4, len5, len6, o, parts, q, ref, ref1, removeAttr, removeAttrs, result, tailDom, u, v, x, y;
    if (dom.nodeType === 8) {
      return dom;
    }
    if (dom.nodeType === 1 && (dom.hasAttribute(cola.constants.IGNORE_DIRECTIVE) || dom.className.indexOf(cola.constants.IGNORE_DIRECTIVE) >= 0)) {
      return dom;
    }
    if (IGNORE_NODES.indexOf(dom.nodeName) > -1) {
      return dom;
    }
    if (dom.nodeType === 3) {
      bindingExpr = dom.nodeValue;
      parts = cola._compileText(bindingExpr);
      if (parts != null ? parts.length : void 0) {
        buildContent(parts, dom, scope);
      }
      return dom;
    } else if (dom.nodeType === 11) {
      child = dom.firstChild;
      while (child) {
        child = _doRenderDomTemplate(child, scope, context);
        child = child.nextSibling;
      }
      return dom;
    }
    initializers = null;
    features = null;
    removeAttrs = null;
    bindingExpr = dom.getAttribute("c-repeat");
    if (bindingExpr) {
      bindingExpr = bindingExpr.replace(ALIAS_REGEXP, context.defaultPath);
      bindingType = "repeat";
      feature = cola._domFeatureBuilder[bindingType](bindingExpr, bindingType, dom);
      if (features == null) {
        features = [];
      }
      features.push(feature);
      dom.removeAttribute("c-repeat");
    } else {
      bindingExpr = dom.getAttribute("c-alias");
      if (bindingExpr) {
        bindingExpr = bindingExpr.replace(ALIAS_REGEXP, context.defaultPath);
        bindingType = "alias";
        feature = cola._domFeatureBuilder[bindingType](bindingExpr, bindingType, dom);
        if (features == null) {
          features = [];
        }
        features.push(feature);
        dom.removeAttribute("c-alias");
      }
    }
    ref = cola._userDomCompiler.$;
    for (l = 0, len1 = ref.length; l < len1; l++) {
      customDomCompiler = ref[l];
      result = customDomCompiler(scope, dom, null, context);
      if (result) {
        if (result instanceof cola._BindingFeature) {
          features.push(result);
        }
        if (typeof result === "function") {
          if (initializers == null) {
            initializers = [];
          }
          initializers.push(result);
        }
      }
    }
    ref1 = dom.attributes;
    for (o = 0, len2 = ref1.length; o < len2; o++) {
      attr = ref1[o];
      attrName = attr.name;
      if (attrName.substring(0, 2) === "c-") {
        if (removeAttrs == null) {
          removeAttrs = [];
        }
        removeAttrs.push(attrName);
        attrValue = attr.value;
        if (attrValue && context.defaultPath) {
          attrValue = attrValue.replace(ALIAS_REGEXP, context.defaultPath);
        }
        if (attrValue) {
          attrName = attrName.substring(2);
          customDomCompiler = cola._userDomCompiler.hasOwnProperty(attrName) && cola._userDomCompiler[attrName];
          if (customDomCompiler) {
            result = customDomCompiler(scope, dom, attr, context);
            if (result) {
              if (result instanceof cola._BindingFeature) {
                features.push(result);
              } else if (result instanceof Array) {
                for (q = 0, len3 = result.length; q < len3; q++) {
                  f = result[q];
                  features.push(f);
                }
              } else if (typeof result === "function") {
                if (initializers == null) {
                  initializers = [];
                }
                initializers.push(result);
              }
            }
          } else {
            if (attrName.indexOf("on") === 0) {
              feature = cola._domFeatureBuilder.event(attrValue, attrName, dom);
            } else {
              builder = cola._domFeatureBuilder.hasOwnProperty(attrName) && cola._domFeatureBuilder[attrName];
              feature = (builder || cola._domFeatureBuilder["$"]).call(cola._domFeatureBuilder, attrValue, attrName, dom);
            }
            if (feature) {
              if (features == null) {
                features = [];
              }
              if (feature instanceof cola._BindingFeature) {
                features.push(feature);
              } else if (feature instanceof Array) {
                for (u = 0, len4 = feature.length; u < len4; u++) {
                  f = feature[u];
                  features.push(f);
                }
              }
            }
          }
        }
      }
    }
    if (removeAttrs) {
      for (x = 0, len5 = removeAttrs.length; x < len5; x++) {
        removeAttr = removeAttrs[x];
        dom.removeAttribute(removeAttr);
      }
    }
    if (features != null ? features.length : void 0) {
      domBinding = cola._domBindingBuilder[bindingType || "$"](dom, scope, features);
      if (scope.data.alias) {
        defaultPath = scope.data.alias;
      }
    }
    if (!cola.util.userData(dom, cola.constants.DOM_SKIP_CHILDREN)) {
      childContext = {};
      for (k in context) {
        v = context[k];
        childContext[k] = v;
      }
      childContext.inRepeatTemplate = context.inRepeatTemplate || bindingType === "repeat";
      if (defaultPath) {
        childContext.defaultPath = defaultPath;
      }
      child = dom.firstChild;
      while (child) {
        child = _doRenderDomTemplate(child, scope, childContext);
        child = child.nextSibling;
      }
    } else {
      cola.util.removeUserData(dom, cola.constants.DOM_SKIP_CHILDREN);
    }
    if (initializers) {
      if (context.inRepeatTemplate || bindingType === "repeat") {
        cola.util.userData(dom, cola.constants.DOM_INITIALIZER_KEY, initializers);
      } else {
        for (y = 0, len6 = initializers.length; y < len6; y++) {
          initializer = initializers[y];
          initializer(scope, dom);
        }
      }
    }
    if (features != null ? features.length : void 0) {
      if (!context.inRepeatTemplate) {
        domBinding.refresh(true);
      }
      if (domBinding instanceof cola._RepeatDomBinding) {
        tailDom = cola.util.userData(domBinding.dom, cola.constants.REPEAT_TAIL_KEY);
        dom = tailDom || domBinding.dom;
      }
    }
    return dom;
  };

  createContentPart = function(part, scope) {
    var domBinding, expression, feature, textNode;
    if (part instanceof cola.Expression) {
      expression = part;
      textNode = document.createElement("span");
      feature = new cola._DomAttrFeature(expression, "text");
      domBinding = new cola._DomBinding(textNode, scope, feature);
      domBinding.refresh();
    } else {
      textNode = document.createTextNode(part);
    }
    return textNode;
  };

  buildContent = function(parts, dom, scope) {
    var childNode, l, len1, part, partNode;
    if (parts.length === 1) {
      childNode = createContentPart(parts[0], scope);
    } else {
      childNode = document.createDocumentFragment();
      for (l = 0, len1 = parts.length; l < len1; l++) {
        part = parts[l];
        partNode = createContentPart(part, scope);
        childNode.appendChild(partNode);
      }
    }
    dom.parentNode.replaceChild(childNode, dom);
  };

  cola._domBindingBuilder = {
    $: function(dom, scope, features) {
      return new cola._DomBinding(dom, scope, features);
    },
    repeat: function(dom, scope, features) {
      var domBinding;
      domBinding = new cola._RepeatDomBinding(dom, scope, features);
      scope = domBinding.scope;
      return domBinding;
    },
    alias: function(dom, scope, features) {
      var domBinding;
      domBinding = new cola._AliasDomBinding(dom, scope, features);
      scope = domBinding.scope;
      return domBinding;
    }
  };

  cola._domFeatureBuilder = {
    $: function(attrValue, attrName, dom) {
      var expression, feature;
      expression = cola._compileExpression(attrValue);
      if (expression) {
        if (attrName === "display") {
          feature = new cola._DisplayFeature(expression);
        } else if (attrName === "options" && dom.nodeName === "SELECT") {
          feature = new cola._SelectOptionsFeature(expression);
        } else {
          feature = new cola._DomAttrFeature(expression, attrName);
        }
      }
      return feature;
    },
    repeat: function(attrValue) {
      var expression;
      expression = cola._compileExpression(attrValue, "repeat");
      if (expression) {
        return new cola._RepeatFeature(expression);
      } else {

      }
    },
    alias: function(attrValue) {
      var expression;
      expression = cola._compileExpression(attrValue, "alias");
      if (expression) {
        return new cola._AliasFeature(expression);
      } else {

      }
    },
    bind: function(attrValue, attrName, dom) {
      var expression, feature, nodeName, type;
      expression = cola._compileExpression(attrValue);
      nodeName = dom.nodeName;
      if (nodeName === "INPUT") {
        type = dom.type;
        if (type === "checkbox") {
          feature = new cola._CheckboxFeature(expression);
        } else if (type === "radio") {
          feature = new cola._RadioFeature(expression);
        } else {
          feature = new cola._TextBoxFeature(expression);
        }
      } else if (nodeName === "SELECT") {
        feature = new cola._SelectFeature(expression);
      } else if (nodeName === "TEXTAREA") {
        feature = new cola._TextBoxFeature(expression);
      } else {
        feature = new cola._DomAttrFeature(expression, "text");
      }
      return feature;
    },
    style: function(attrValue) {
      var expression, feature, features, style, styleExpr, styleProp;
      if (!attrValue) {
        return false;
      }
      style = cola.util.parseStyleLikeString(attrValue);
      features = [];
      for (styleProp in style) {
        styleExpr = style[styleProp];
        expression = cola._compileExpression(styleExpr);
        if (expression) {
          feature = new cola._DomStylePropFeature(expression, styleProp);
          features.push(feature);
        }
      }
      return features;
    },
    classname: function(attrValue) {
      var classConfig, classExpr, className, expression, feature, features;
      if (!attrValue) {
        return false;
      }
      features = [];
      try {
        expression = cola._compileExpression(attrValue);
        if (expression) {
          feature = new cola._DomClassFeature(expression);
          features.push(feature);
        }
      } catch (_error) {
        classConfig = cola.util.parseStyleLikeString(attrValue);
        for (className in classConfig) {
          classExpr = classConfig[className];
          expression = cola._compileExpression(classExpr);
          if (expression) {
            feature = new cola._DomToggleClassFeature(expression, className);
            features.push(feature);
          }
        }
      }
      return features;
    },
    "class": function() {
      return this.classname.apply(this, arguments);
    },
    resource: function(attrValue, attrName, dom) {
      attrValue = cola.util.trim(attrValue);
      if (attrValue) {
        $fly(dom).text(cola.resource(attrValue));
      }
    },
    watch: function(attrValue) {
      var action, feature, i, l, len1, path, pathStr, paths, ref;
      i = attrValue.indexOf(" on ");
      if (i > 0) {
        action = attrValue.substring(0, i);
        pathStr = attrValue.substring(i + 4);
        if (pathStr) {
          paths = [];
          ref = pathStr.split(",");
          for (l = 0, len1 = ref.length; l < len1; l++) {
            path = ref[l];
            path = cola.util.trim(path);
            if (path) {
              paths.push(path);
            }
          }
          if (paths.length) {
            feature = new cola._WatchFeature(action, paths);
          }
        }
      }
      if (!feature) {
        throw new cola.Exception("\"" + expr + "\" is not a valid watch expression.");
      }
      return feature;
    },
    event: function(attrValue, attrName) {
      var expression, feature;
      expression = cola._compileExpression(attrValue);
      if (expression) {
        feature = new cola._EventFeature(expression, attrName.substring(2));
      }
      return feature;
    }
  };

  (function() {
    var escape, isStyleFuncSupported;
    escape = function(text) {
      return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
    };
    isStyleFuncSupported = !!CSSStyleDeclaration.prototype.getPropertyValue;
    if (!isStyleFuncSupported) {
      CSSStyleDeclaration.prototype.getPropertyValue = function(a) {
        return this.getAttribute(a);
      };
      CSSStyleDeclaration.prototype.setProperty = function(styleName, value, priority) {
        var rule;
        this.setAttribute(styleName, value);
        priority = typeof priority !== 'undefined' ? priority : '';
        if (priority !== '') {
          rule = new RegExp(escape(styleName) + '\\s*:\\s*' + escape(value)(+'(\\s*;)?', 'gmi'));
          return this.cssText = this.cssText.replace(rule, styleName + ': ' + value + ' !' + priority + ';');
        }
      };
      CSSStyleDeclaration.prototype.removeProperty = function(a) {
        return this.removeAttribute(a);
      };
      return CSSStyleDeclaration.prototype.getPropertyPriority = function(styleName) {
        var rule;
        rule = new RegExp(escape(styleName) + '\\s*:\\s*[^\\s]*\\s*!important(\\s*;)?', 'gmi');
        if (rule.test(this.cssText)) {
          return 'important';
        } else {
          return '';
        }
      };
    }
  })();

  cola.util.addClass = function(dom, value, continuous) {
    var className;
    if (!continuous) {
      $(dom).addClass(value);
      return cola.util;
    }
    if (dom.nodeType === 1) {
      className = dom.className ? (" " + dom.className + " ").replace(cola.constants.CLASS_REG, " ") : " ";
      if (className.indexOf(" " + value + " ") < 0) {
        className += value + " ";
        dom.className = $.trim(className);
      }
    }
    return cola.util;
  };

  cola.util.removeClass = function(dom, value, continuous) {
    var className;
    if (!continuous) {
      $(dom).removeClass(value);
      return cola.util;
    }
    if (dom.nodeType === 1) {
      className = dom.className ? (" " + dom.className + " ").replace(cola.constants.CLASS_REG, " ") : " ";
      if (className.indexOf(" " + value + " ") >= 0) {
        className = className.replace(" " + value + " ", " ");
        dom.className = $.trim(className);
      }
    }
    return cola.util;
  };

  cola.util.toggleClass = function(dom, value, stateVal, continuous) {
    if (!continuous) {
      $(dom).toggleClass(value, !!stateVal);
      return;
    }
    if (dom.nodeType === 1) {
      if (!!stateVal) {
        this._addClass(dom, value, true);
      } else {
        this._removeClass(dom, value, true);
      }
    }
    return cola.util;
  };

  cola.util.hasClass = function(dom, className) {
    var domClassName, l, len1, name, names;
    names = className.split(/\s+/g);
    domClassName = dom.className ? (" " + dom.className + " ").replace(cola.constants.CLASS_REG, " ") : " ";
    for (l = 0, len1 = names.length; l < len1; l++) {
      name = names[l];
      if (domClassName.indexOf(" " + name + " ") < 0) {
        return false;
      }
    }
    return true;
  };

  cola.util.style = function(dom, styleName, value, priority) {
    var style;
    style = dom.style;
    if (typeof styleName !== 'undefined') {
      if (typeof value !== 'undefined') {
        priority = typeof priority !== 'undefined' ? priority : '';
        return style.setProperty(styleName, value, priority);
      } else {
        return style.getPropertyValue(styleName);
      }
    } else {
      return style;
    }
  };

  cola.util.getTextChildData = function(dom) {
    var child;
    child = dom.firstChild;
    while (child) {
      if (child.nodeType === 3) {
        return child.textContent;
      }
      child = child.nextSibling;
    }
    return null;
  };

  cola.util.eachNodeChild = function(node, fn) {
    var child;
    if (!node || !fn) {
      return cola.util;
    }
    child = node.firstChild;
    while (child) {
      if (fn(child) === false) {
        break;
      }
      child = child.nextSibling;
    }
    return cola.util;
  };

  cola.util.hasContent = function(dom) {
    var child;
    child = dom.firstChild;
    while (child) {
      if (child.nodeType === 3 || child.nodeType === 1) {
        return true;
      }
      child = child.nextSibling;
    }
    return false;
  };

  cola.util.getScrollerRender = function(element) {
    var helperElem, perspectiveProperty, transformProperty;
    helperElem = document.createElement("div");
    perspectiveProperty = cola.Fx.perspectiveProperty;
    transformProperty = cola.Fx.transformProperty;
    if (helperElem.style[perspectiveProperty] !== void 0) {
      return function(left, top, zoom) {
        element.style[transformProperty] = 'translate3d(' + (-left) + 'px,' + (-top) + 'px,0) scale(' + zoom + ')';
      };
    } else if (helperElem.style[transformProperty] !== void 0) {
      return function(left, top, zoom) {
        element.style[transformProperty] = 'translate(' + (-left) + 'px,' + (-top) + 'px) scale(' + zoom + ')';
      };
    } else {
      return function(left, top, zoom) {
        element.style.marginLeft = left ? (-left / zoom) + 'px' : '';
        element.style.marginTop = top ? (-top / zoom) + 'px' : '';
        element.style.zoom = zoom || '';
      };
    }
  };

  cola.util.getType = (function() {
    var classToType, l, len1, name, ref;
    classToType = {};
    ref = "Boolean Number String Function Array Date RegExp Undefined Null".split(" ");
    for (l = 0, len1 = ref.length; l < len1; l++) {
      name = ref[l];
      classToType["[object " + name + "]"] = name.toLowerCase();
    }
    return function(obj) {
      var strType;
      strType = Object.prototype.toString.call(obj);
      return classToType[strType] || "object";
    };
  })();

  cola.util.typeOf = function(obj, type) {
    return cola.util.getType(obj) === type;
  };

  cola.util.getDomRect = function(dom) {
    var rect;
    rect = dom.getBoundingClientRect();
    if (isNaN(rect.height)) {
      rect.height = rect.bottom - rect.top;
    }
    if (isNaN(rect.width)) {
      rect.width = rect.right - rect.left;
    }
    return rect;
  };

  $.xCreate.templateProcessors.push(function(template) {
    var dom;
    if (template instanceof cola.Widget) {
      dom = template.getDom();
      dom.setAttribute(cola.constants.IGNORE_DIRECTIVE, "");
      return dom;
    }
  });

  $.xCreate.attributeProcessor["c-widget"] = function($dom, attrName, attrValue, context) {
    var configKey, widgetConfigs;
    if (!attrValue) {
      return;
    }
    if (typeof attrValue === "string") {
      $dom.attr(attrName, attrValue);
    } else if (context) {
      configKey = cola.uniqueId();
      $dom.attr("c-widget-config", configKey);
      widgetConfigs = context.widgetConfigs;
      if (!widgetConfigs) {
        context.widgetConfigs = widgetConfigs = {};
      }
      widgetConfigs[configKey] = attrValue;
    }
  };

  cola.xRender.nodeProcessors.push(function(node, context) {
    var dom, widget;
    if (node instanceof cola.Widget) {
      widget = node;
    } else if (node.$type) {
      widget = cola.widget(node, context.namespace);
    }
    if (widget) {
      dom = widget.getDom();
      dom.setAttribute(cola.constants.IGNORE_DIRECTIVE, "");
    }
    return dom;
  });

  cola.Model.prototype.widgetConfig = function(id, config) {
    var k, ref, v;
    if (arguments.length === 1) {
      if (typeof id === "string") {
        return (ref = this._widgetConfig) != null ? ref[id] : void 0;
      } else {
        config = id;
        for (k in config) {
          v = config[k];
          this.widgetConfig(k, v);
        }
      }
    } else {
      if (this._widgetConfig == null) {
        this._widgetConfig = {};
      }
      this._widgetConfig[id] = config;
    }
  };

  ALIAS_REGEXP = new RegExp("\\$default", "g");

  _findWidgetConfig = function(scope, name) {
    var ref, widgetConfig;
    while (scope) {
      widgetConfig = (ref = scope._widgetConfig) != null ? ref[name] : void 0;
      if (widgetConfig) {
        break;
      }
      scope = scope.parent;
    }
    return widgetConfig;
  };

  _compileWidgetDom = function(dom, widgetType, config) {
    var attr, attrName, isEvent, l, len1, len2, o, prop, ref, removeAttrs;
    if (config == null) {
      config = {};
    }
    if (!widgetType.attributes._inited || !widgetType.events._inited) {
      cola.preprocessClass(widgetType);
    }
    config.$constr = widgetType;
    removeAttrs = null;
    ref = dom.attributes;
    for (l = 0, len1 = ref.length; l < len1; l++) {
      attr = ref[l];
      attrName = attr.name;
      if (attrName.indexOf("c-") === 0) {
        prop = attrName.slice(2);
        if (widgetType.attributes.$has(prop) && prop !== "class") {
          if (prop === "bind") {
            config[prop] = attr.value;
          } else {
            config[prop] = cola._compileExpression(attr.value);
          }
          if (removeAttrs == null) {
            removeAttrs = [];
          }
          removeAttrs.push(attrName);
        } else {
          isEvent = widgetType.events.$has(prop);
          if (!isEvent && prop.indexOf("on") === 0) {
            if (widgetType.events.$has(prop.slice(2))) {
              isEvent = true;
              prop = prop.slice(2);
            }
          }
          if (isEvent) {
            config[prop] = cola._compileExpression(attr.value);
            if (removeAttrs == null) {
              removeAttrs = [];
            }
            removeAttrs.push(attrName);
          }
        }
      } else {
        prop = attrName;
        if (widgetType.attributes.$has(prop)) {
          config[prop] = attr.value;
        } else {
          isEvent = widgetType.events.$has(prop);
          if (!isEvent && prop.indexOf("on") === 0) {
            if (widgetType.events.$has(prop.slice(2))) {
              isEvent = true;
              prop = prop.slice(2);
              if (removeAttrs == null) {
                removeAttrs = [];
              }
              removeAttrs.push(attrName);
            }
          }
          if (isEvent) {
            config[prop] = attr.value;
          }
        }
      }
    }
    if (removeAttrs) {
      for (o = 0, len2 = removeAttrs.length; o < len2; o++) {
        attr = removeAttrs[o];
        dom.removeAttribute(attr);
      }
    }
    return config;
  };

  _compileWidgetAttribute = function(scope, dom, context) {
    var config, importConfig, importName, importNames, ip, iv, l, len1, p, v, widgetConfigStr;
    widgetConfigStr = dom.getAttribute("c-widget");
    if (widgetConfigStr) {
      dom.removeAttribute("c-widget");
      if (context.defaultPath) {
        widgetConfigStr = widgetConfigStr.replace(ALIAS_REGEXP, context.defaultPath);
      }
      config = cola.util.parseStyleLikeString(widgetConfigStr, "$type");
      if (config) {
        importNames = null;
        for (p in config) {
          v = config[p];
          importName = null;
          if (p.charCodeAt(0) === 35) {
            importName = p.substring(1);
          } else if (p === "$type" && typeof v === "string" && v.charCodeAt(0) === 35) {
            importName = v.substring(1);
          }
          if (importName) {
            delete config[p];
            if (importNames == null) {
              importNames = [];
            }
            importNames.push(importName);
          }
        }
        if (importNames) {
          for (l = 0, len1 = importNames.length; l < len1; l++) {
            importName = importNames[l];
            importConfig = _findWidgetConfig(scope, importName);
            if (importConfig) {
              for (ip in importConfig) {
                iv = importConfig[ip];
                config[ip] = iv;
              }
            }
          }
        }
      }
    }
    return config;
  };

  cola._userDomCompiler.$.push(function(scope, dom, attr, context) {
    var config, configKey, constr, jsonConfig, k, parentWidget, ref, ref1, tagName, v, widgetType;
    if (cola.util.userData(dom, cola.constants.DOM_ELEMENT_KEY)) {
      return null;
    }
    if (dom.nodeType !== 1) {
      return null;
    }
    if (dom.id) {
      jsonConfig = _findWidgetConfig(scope, dom.id);
    }
    parentWidget = context.parentWidget;
    tagName = dom.tagName;
    configKey = dom.getAttribute("c-widget-config");
    if (configKey) {
      dom.removeAttribute("c-widget-config");
      config = (ref = context.widgetConfigs) != null ? ref[configKey] : void 0;
    } else {
      config = _compileWidgetAttribute(scope, dom, context);
      if (!config || (!config.$type && !config.$constr)) {
        widgetType = parentWidget != null ? (ref1 = parentWidget.childTagNames) != null ? ref1[tagName] : void 0 : void 0;
        if (widgetType == null) {
          widgetType = WIDGET_TAGS_REGISTRY[tagName];
        }
        if (widgetType) {
          config = _compileWidgetDom(dom, widgetType, config);
        }
      }
    }
    if (!(config || jsonConfig)) {
      return null;
    }
    if (config == null) {
      config = {};
    }
    if (jsonConfig) {
      for (k in jsonConfig) {
        v = jsonConfig[k];
        if (!config.hasOwnProperty(k)) {
          config[k] = v;
        }
      }
    }
    if (typeof config === "string") {
      config = {
        $type: config
      };
    }
    if (config.$constr instanceof Function) {
      constr = config.$constr;
    } else {
      constr = cola.resolveType((parentWidget != null ? parentWidget.CHILDREN_TYPE_NAMESPACE : void 0) || "widget", config, cola.Widget);
    }
    config.$constr = context.parentWidget = constr;
    if (cola.util.isCompatibleType(cola.AbstractLayer, constr) && config.lazyRender) {
      cola.util.userData(dom, cola.constants.DOM_SKIP_CHILDREN, true);
    }
    return function(scope, dom) {
      var oldScope, widget;
      context.parentWidget = parentWidget;
      config.dom = dom;
      oldScope = cola.currentScope;
      cola.currentScope = scope;
      try {
        widget = cola.widget(config);
        return widget;
      } finally {
        cola.currentScope = oldScope;
      }
    };
  });

  cola.registerTypeResolver("widget", function(config) {
    if (!config) {
      return;
    }
    if (config.$constructor && cola.util.isSuperClass(cola.Widget, config.$constructor)) {
      return config.$constructor;
    }
    if (config.$type) {
      return cola[cola.util.capitalize(config.$type)];
    }
  });

  cola.registerType("widget", "_default", cola.Widget);

  cola.widget = function(config, namespace, model) {
    var c, constr, e, ele, group, l, len1, len2, o, widget;
    if (!config) {
      return null;
    }
    if (typeof config === "string") {
      ele = window[config];
      if (!ele) {
        return null;
      }
      if (ele.nodeType) {
        widget = cola.util.userData(ele, cola.constants.DOM_ELEMENT_KEY);
        if (model && widget._scope !== model) {
          widget = null;
        }
        if (widget instanceof cola.Widget) {
          return widget;
        } else {
          return null;
        }
      } else {
        group = [];
        for (l = 0, len1 = ele.length; l < len1; l++) {
          e = ele[l];
          widget = cola.util.userData(e, cola.constants.DOM_ELEMENT_KEY);
          if (widget instanceof cola.Widget && (!model || widget._scope === model)) {
            group.push(widget);
          }
        }
        if (!group.length) {
          return null;
        } else if (group.length === 1) {
          return group[0];
        } else {
          return cola.Element.createGroup(group);
        }
      }
    } else {
      if (config instanceof Array) {
        group = [];
        for (o = 0, len2 = config.length; o < len2; o++) {
          c = config[o];
          group.push(cola.widget(c, namespace, model));
        }
        return cola.Element.createGroup(group);
      } else if (config.nodeType === 1) {
        widget = cola.util.userData(config, cola.constants.DOM_ELEMENT_KEY);
        if (model && widget._scope !== model) {
          widget = null;
        }
        if (widget instanceof cola.Widget) {
          return widget;
        } else {
          return null;
        }
      } else {
        constr = config.$constr || cola.resolveType(namespace || "widget", config, cola.Widget);
        if (model && !config.scope) {
          config.scope = model;
        }
        return new constr(config);
      }
    }
  };

  cola.findWidget = function(dom, type) {
    var find, typeName;
    if (type && typeof type === "string") {
      typeName = type;
      type = WIDGET_TAGS_REGISTRY[typeName];
      if (!type) {
        type = cola.resolveType("widget", {
          $type: typeName
        });
      }
      if (!type) {
        return null;
      }
    }
    if (dom instanceof cola.Widget) {
      dom = dom.getDom();
    }
    find = function(win, dom, type) {
      var frame, parentDom, parentFrames, widget;
      parentDom = dom.parentNode;
      while (parentDom) {
        dom = parentDom;
        widget = cola.util.userData(dom, cola.constants.DOM_ELEMENT_KEY);
        if (widget) {
          if (!type || widget instanceof type) {
            return widget;
          }
        }
        parentDom = dom.parentNode;
      }
      if (win.parent) {
        try {
          parentFrames = win.parent.jQuery("iframe,frame");
        } catch (_error) {

        }
        if (parentFrames) {
          frame = null;
          parentFrames.each(function() {
            if (this.contentWindow === win) {
              frame = this;
              return false;
            }
          });
          if (frame) {
            widget = find(win.parent, frame, type);
          }
        }
      }
      return widget;
    };
    return find(window, dom, type);
  };

  cola.Model.prototype.widget = function(config) {
    return cola.widget(config, null, this);
  };


  /*
  User Widget
   */

  WIDGET_TAGS_REGISTRY = {};

  _extendWidget = function(superCls, definition) {
    var cls, def, prop, ref, template;
    cls = function(config) {
      if (!cls.attributes._inited || !cls.events._inited) {
        cola.preprocessClass(cls);
      }
      if (definition.create) {
        this.on("create", definition.create);
      }
      if (definition.destroy) {
        this.on("destroy", definition.destroy);
      }
      if (definition.initDom) {
        this.on("initDom", (function(_this) {
          return function(self, arg) {
            return _this.initDom(arg.dom);
          };
        })(this));
      }
      if (definition.refreshDom) {
        this.on("refreshDom", (function(_this) {
          return function(self, arg) {
            return _this.refreshDom(arg.dom);
          };
        })(this));
      }
      this.on("attributeChange", (function(_this) {
        return function(self, arg) {
          var attr;
          attr = arg.attribute;
          _this._widgetModel.data._onDataMessage(["@" + attr], cola.constants.MESSAGE_PROPERTY_CHANGE, {});
          value = _this._get(attr);
          if (value && (value instanceof cola.Entity || value instanceof cola.EntityList)) {
            _this._entityProps[attr] = value;
          } else {
            delete _this._entityProps[attr];
          }
        };
      })(this));
      this._entityProps = {};
      this._widgetModel = new cola.WidgetModel(this, (config != null ? config.scope : void 0) || cola.currentScope);
      cls.__super__.constructor.call(this, config);
    };
    extend(cls, superCls);
    cls.tagName = ((ref = definition.tagName) != null ? ref.toUpperCase() : void 0) || "";
    if (definition.parentWidget) {
      cls.parentWidget = definition.parentWidget;
    }
    cls.attributes = definition.attributes || {};
    cls.attributes.widgetModel = {
      readOnly: true,
      getter: function() {
        return this._widgetModel;
      }
    };
    cls.attributes.template = {
      readOnlyAfterCreate: true
    };
    if (definition.events) {
      cls.events = definition.events;
    }
    if (definition.template) {
      template = definition.template;
      if (typeof template === "string" && template.match(/^\#[\w\-\$]*$/)) {
        template = document.getElementById(template.substring(1));
        if (template) {
          definition.template = template.innerHTML;
          $fly(template).remove();
        }
      }
      cls.attributes.template = {
        defaultValue: definition.template
      };
    }
    cls.prototype._createDom = function() {
      var dom;
      if (this._template) {
        dom = cola.xRender(this._template || {}, this._widgetModel);
        this._domCreated = true;
        return dom;
      } else {
        return superCls.prototype._createDom.apply(this);
      }
    };
    cls.prototype._initDom = function(dom) {
      var attr, attrName, cssName, l, len1, ref1, templateDom;
      superCls.prototype._initDom.call(this, dom);
      template = this._template;
      if (template && !this._domCreated) {
        templateDom = this.xRender(template);
        if ((templateDom != null ? templateDom.nodeType : void 0) === 11) {
          templateDom = templateDom.firstElementChild;
        }
        if (templateDom) {
          if (templateDom.attributes) {
            ref1 = templateDom.attributes;
            for (l = 0, len1 = ref1.length; l < len1; l++) {
              attr = ref1[l];
              attrName = attr.name;
              if (attrName === "class") {
                $fly(dom).addClass(attr.value);
              } else if (attrName !== "style") {
                if (!dom.hasAttribute(attrName)) {
                  dom.setAttribute(attrName, attr.value);
                }
              }
            }
          }
          for (cssName in templateDom.style) {
            if (dom.style[cssName] === "") {
              dom.style[cssName] = templateDom.style[cssName];
            }
          }
          while (templateDom.firstChild) {
            dom.appendChild(templateDom.firstChild);
          }
        }
      }
    };
    cls.prototype.xRender = function(template, context) {
      return cola.xRender(template, this._widgetModel, context);
    };
    for (prop in definition) {
      def = definition[prop];
      if (definition.hasOwnProperty(prop) && typeof def === "function") {
        cls.prototype[prop] = def;
      }
    }
    return cls;
  };

  cola.defineWidget = function(type, definition) {
    var childTagNames, ref, tagName;
    if (!cola.util.isSuperClass(cola.Widget, type)) {
      definition = type;
      type = cola.TemplateWidget;
    }
    if (definition) {
      type = _extendWidget(type, definition);
    }
    tagName = (ref = type.tagName) != null ? ref.toUpperCase() : void 0;
    if (tagName && type.parentWidget) {
      childTagNames = type.parentWidget.childTagNames;
      if (!childTagNames) {
        type.parentWidget.childTagNames = childTagNames = {};
      }
      if (childTagNames[tagName]) {
        throw new cola.Exception("Tag name \"" + tagName + "\" is already registered in \"" + type.parentWidget.tagName + "\".");
      }
      childTagNames[tagName] = type;
    } else if (tagName) {
      if (WIDGET_TAGS_REGISTRY[tagName]) {
        throw new cola.Exception("Tag name \"" + tagName + "\" is already registered.");
      }
      WIDGET_TAGS_REGISTRY[tagName] = type;
    }
    return type;
  };

  cola.registerWidget = cola.defineWidget;

  ACTIVE_PINCH_REG = /^pinch/i;

  ACTIVE_ROTATE_REG = /^rotate/i;

  PAN_VERTICAL_events = ["panUp", "panDown"];

  SWIPE_VERTICAL_events = ["swipeUp", "swipeDown"];

  if (typeof document.documentElement.style.flex !== "string") {
    $(document.documentElement).addClass("flex-unsupported");
  }


  /*
      ClassName池对象
      用于刷新组件时频繁的编辑class name提高性能
   */

  cola.ClassNamePool = (function() {
    function ClassNamePool(domClass, semanticList) {
      if (semanticList == null) {
        semanticList = [];
      }
      this.elements = [];
      domClass = domClass ? (" " + domClass + " ").replace(cola.constants.CLASS_REG, " ") : " ";
      semanticList.forEach((function(_this) {
        return function(name) {
          var klass;
          klass = " " + name + " ";
          if (domClass.indexOf(klass) > -1) {
            domClass = domClass.replace(klass, " ");
            _this.add(name);
          }
        };
      })(this));
      $.trim(domClass).split(" ").forEach((function(_this) {
        return function(klass) {
          _this.add(klass);
        };
      })(this));
    }

    ClassNamePool.prototype.add = function(className) {
      var index;
      if (!className) {
        return;
      }
      index = this.elements.indexOf(className);
      if (index > -1) {
        return;
      }
      return this.elements.push(className);
    };

    ClassNamePool.prototype.remove = function(className) {
      var i;
      i = this.elements.indexOf(className);
      if (i > -1) {
        this.elements.splice(i, 1);
      }
      return this;
    };

    ClassNamePool.prototype.destroy = function() {
      return delete this["elements"];
    };

    ClassNamePool.prototype.join = function() {
      return this.elements.join(" ");
    };

    ClassNamePool.prototype.toggle = function(className, status) {
      if (!!status) {
        this.add(className);
      } else {
        this.remove(className);
      }
    };

    return ClassNamePool;

  })();

  _destroyRenderableElement = function(node, data) {
    var element;
    element = data[cola.constants.DOM_ELEMENT_KEY];
    if (!(typeof element === "function" ? element(_destroyed) : void 0)) {
      element._domRemoved = true;
      element.destroy();
    }
  };


  /*
      可渲染元素
   */

  cola.RenderableElement = (function(superClass) {
    extend(RenderableElement, superClass);

    RenderableElement.events = {
      initDom: null,
      refreshDom: null
    };

    function RenderableElement(config) {
      var dom;
      if (config) {
        dom = config.dom;
        if (dom) {
          delete config.dom;
        }
      }
      if (this._doms == null) {
        this._doms = {};
      }
      RenderableElement.__super__.constructor.call(this, config);
      if (dom) {
        this._setDom(dom, true);
      }
    }

    RenderableElement.prototype._initDom = function(dom) {};

    RenderableElement.prototype._parseDom = function(dom) {};

    RenderableElement.prototype._setDom = function(dom, parseChild) {
      var arg;
      if (!dom) {
        return;
      }
      this._dom = dom;
      cola.util.userData(dom, cola.constants.DOM_ELEMENT_KEY, this);
      cola.util.onNodeDispose(dom, _destroyRenderableElement);
      if (parseChild) {
        this._parseDom(dom);
      }
      this._initDom(dom);
      arg = {
        dom: dom
      };
      this.fire("initDom", this, arg);
      this._refreshDom();
      this._rendered = true;
    };

    RenderableElement.prototype._createDom = function() {
      var className, dom;
      dom = document.createElement(this.constructor.tagName || "div");
      className = this.constructor.CLASS_NAME || "";
      if (className) {
        $fly(dom).addClass("ui " + className);
      }
      return dom;
    };

    RenderableElement.prototype._doSet = function(attr, attrConfig, value) {
      if ((attrConfig != null ? attrConfig.refreshDom : void 0) && this._dom) {
        cola.util.delay(this, "refreshDom", 50, this._refreshDom);
      }
      return RenderableElement.__super__._doSet.call(this, attr, attrConfig, value);
    };

    RenderableElement.prototype._doRefreshDom = function() {
      var className, l, len1, name, names;
      cola.util.cancelDelay(this, "_refreshDom");
      if (!this._dom) {
        return;
      }
      className = this.constructor.CLASS_NAME;
      if (className) {
        this._classNamePool.add("ui");
        names = $.trim(className).split(" ");
        for (l = 0, len1 = names.length; l < len1; l++) {
          name = names[l];
          this._classNamePool.add(name);
        }
      }
      this._resetDimension();
    };

    RenderableElement.prototype._refreshDom = function() {
      var newClassName;
      if (!(this._dom || !this._destroyed)) {
        return;
      }
      this._classNamePool = new cola.ClassNamePool(this._dom.className, this.constructor.SEMANTIC_CLASS);
      this._doRefreshDom();
      newClassName = $.trim(this._classNamePool.join());
      this._dom.className = newClassName;
      this._classNamePool.destroy();
      delete this["_classNamePool"];
    };

    RenderableElement.prototype._resetDimension = function() {};

    RenderableElement.prototype.getDom = function() {
      var dom;
      if (this._destroyed) {
        return null;
      }
      if (!this._dom) {
        dom = this._createDom();
        this._setDom(dom);
      }
      return this._dom;
    };

    RenderableElement.prototype.get$Dom = function() {
      if (this._destroyed) {
        return null;
      }
      if (this._$dom == null) {
        this._$dom = $(this.getDom());
      }
      return this._$dom;
    };

    RenderableElement.prototype.refresh = function() {
      var arg;
      if (!this._dom) {
        return this;
      }
      this._refreshDom();
      arg = {
        dom: this._dom
      };
      this.fire("refreshDom", this, arg);
      return this;
    };

    RenderableElement.prototype.appendTo = function(parentNode) {
      if (parentNode && this.getDom()) {
        $(parentNode).append(this._dom);
      }
      return this;
    };

    RenderableElement.prototype.remove = function() {
      this.get$Dom().remove();
      return this;
    };

    RenderableElement.prototype.destroy = function() {
      if (this._destroyed) {
        return;
      }
      cola.util.cancelDelay(this, "refreshDom");
      if (this._dom) {
        if (!this._domRemoved) {
          this.remove();
        }
        delete this._dom;
        delete this._$dom;
      }
      RenderableElement.__super__.destroy.call(this);
      this._destroyed = true;
    };

    RenderableElement.prototype.addClass = function(value, continuous) {
      if (continuous) {
        cola.util.addClass(this._dom, value, true);
      } else {
        this.get$Dom().addClass(value);
      }
      return this;
    };

    RenderableElement.prototype.removeClass = function(value, continuous) {
      if (continuous) {
        cola.util.removeClass(this._dom, value, true);
      } else {
        this.get$Dom().removeClass(value);
      }
      return this;
    };

    RenderableElement.prototype.toggleClass = function(value, state, continuous) {
      if (continuous) {
        cola.util.toggleClass(this._dom, value, state, true);
      } else {
        this.get$Dom().toggleClass(value, state);
      }
      return this;
    };

    RenderableElement.prototype.hasClass = function(value, continuous) {
      if (continuous) {
        return cola.util.hasClass(this._dom, value, true);
      } else {
        return this.get$Dom().hasClass(value);
      }
    };

    return RenderableElement;

  })(cola.Element);


  /*
  Dorado 基础组件
   */

  cola.Widget = (function(superClass) {
    extend(Widget, superClass);

    function Widget() {
      return Widget.__super__.constructor.apply(this, arguments);
    }

    Widget.SEMANTIC_CLASS = ["left floated", "right floated"];

    Widget.attributes = {
      display: {
        defaultValue: true,
        refreshDom: true,
        type: "boolean"
      },
      float: {
        refreshDom: true,
        "enum": ["left", "right", ""],
        defaultValue: "",
        setter: function(value) {
          var oldValue;
          oldValue = this["_float"];
          if (this._dom && oldValue && oldValue !== value) {
            cola.util.removeClass(this._dom, oldValue + " floated", true);
          }
          this["_float"] = value;
        }
      },
      "class": {
        refreshDom: true,
        setter: function(value) {
          var oldValue;
          oldValue = this["_class"];
          if (oldValue && this._dom && oldValue !== value) {
            this.get$Dom().removeClass(oldValue);
          }
          this["_class"] = value;
        }
      },
      popup: null,
      dimmer: {
        setter: function(value) {
          var k, v;
          if (this._dimmer == null) {
            this._dimmer = {};
          }
          for (k in value) {
            v = value[k];
            this._dimmer[k] = v;
          }
        }
      },
      height: {
        refreshDom: true
      },
      width: {
        refreshDom: true
      }
    };

    Widget.events = {
      click: {
        $event: "click"
      },
      dblClick: {
        $event: "dblclick"
      },
      mouseDown: {
        $event: "mousedown"
      },
      mouseUp: {
        $event: "mouseup"
      },
      tap: {
        hammerEvent: "tap"
      },
      press: {
        hammerEvent: "press"
      },
      panStart: {
        hammerEvent: "panstart"
      },
      panMove: {
        hammerEvent: "panmove"
      },
      panEnd: {
        hammerEvent: "panend"
      },
      panCancel: {
        hammerEvent: "pancancel"
      },
      panLeft: {
        hammerEvent: "panleft"
      },
      panRight: {
        hammerEvent: "panright"
      },
      panUp: {
        hammerEvent: "panup"
      },
      panDown: {
        hammerEvent: "pandown"
      },
      pinchStart: {
        hammerEvent: "pinchstart"
      },
      pinchMove: {
        hammerEvent: "pinchmove"
      },
      pinchEnd: {
        hammerEvent: "pinchend"
      },
      pinchCancel: {
        hammerEvent: "pinchcancel"
      },
      pinchIn: {
        hammerEvent: "pinchin"
      },
      pinchOut: {
        hammerEvent: "pinchout"
      },
      rotateStart: {
        hammerEvent: "rotatestart"
      },
      rotateMove: {
        hammerEvent: "rotatemove"
      },
      rotateEnd: {
        hammerEvent: "rotateend"
      },
      rotateCancel: {
        hammerEvent: "rotatecancel"
      },
      swipeLeft: {
        hammerEvent: "swipeleft"
      },
      swipeRight: {
        hammerEvent: "swiperight"
      },
      swipeUp: {
        hammerEvent: "swipeup"
      },
      swipeDown: {
        hammerEvent: "swipedown"
      }
    };

    Widget.prototype._initDom = function(dom) {
      var popup, popupOptions;
      Widget.__super__._initDom.call(this, dom);
      popup = this._popup;
      if (popup) {
        popupOptions = {};
        if (typeof popup === "string" || (popup.constructor === Object.prototype.constructor && popup.tagName) || popup.nodeType === 1) {
          popupOptions.html = cola.xRender(popup);
        } else if (popup.constructor === Object.prototype.constructor) {
          popupOptions = popup;
          if (popupOptions.content) {
            popupOptions.html = cola.xRender(popupOptions.content);
          } else if (popupOptions.html) {
            popupOptions.html = cola.xRender(popupOptions.html);
          }
        }
        return $(dom).popup(popupOptions);
      }
    };

    Widget.prototype._setDom = function(dom, parseChild) {
      var eventName;
      if (!dom) {
        return;
      }
      Widget.__super__._setDom.call(this, dom, parseChild);
      for (eventName in this.constructor.events) {
        if (this.getListeners(eventName)) {
          this._bindEvent(eventName);
        }
      }
    };

    Widget.prototype._on = function(eventName, listener, alias) {
      Widget.__super__._on.call(this, eventName, listener, alias);
      if (this._dom) {
        this._bindEvent(eventName);
      }
      return this;
    };

    Widget.prototype.fire = function(eventName, self, arg) {
      var eventConfig;
      if (!this._eventRegistry) {
        return;
      }
      eventConfig = this.constructor.events.$get(eventName);
      if (this.constructor.attributes.hasOwnProperty("disabled") && this.get("disabled") && eventConfig && (eventConfig.$event || eventConfig.hammerEvent)) {
        return;
      }
      if (!this["_hasFireTapEvent"]) {
        this["_hasFireTapEvent"] = eventName === "tap";
      }
      if (eventName === "click" && this["_hasFireTapEvent"]) {
        return;
      }
      return Widget.__super__.fire.call(this, eventName, self, arg);
    };

    Widget.prototype._doRefreshDom = function() {
      var l, len1, name, ref;
      if (!this._dom) {
        return;
      }
      Widget.__super__._doRefreshDom.call(this);
      if (this._float) {
        this._classNamePool.add(this._float + " floated");
      }
      this._classNamePool.toggle("display-none", !!!this._display);
      if (!this._rendered && this._class) {
        ref = this._class.split(" ");
        for (l = 0, len1 = ref.length; l < len1; l++) {
          name = ref[l];
          this._classNamePool.add(name);
        }
      }
    };

    Widget.prototype._bindEvent = function(eventName) {
      var $dom, eventConfig;
      if (!this._dom) {
        return;
      }
      if (this._bindedEvents == null) {
        this._bindedEvents = {};
      }
      if (this._bindedEvents[eventName]) {
        return;
      }
      $dom = this.get$Dom();
      eventConfig = this.constructor.events.$get(eventName);
      if (eventConfig != null ? eventConfig.$event : void 0) {
        $dom.on(eventConfig.$event, (function(_this) {
          return function(evt) {
            var arg;
            arg = {
              dom: _this._dom,
              event: evt
            };
            return _this.fire(eventName, _this, arg);
          };
        })(this));
        this._bindedEvents[eventName] = true;
        return;
      }
      if (eventConfig != null ? eventConfig.hammerEvent : void 0) {
        if (this._hammer == null) {
          this._hammer = new Hammer(this._dom, {});
        }
        if (ACTIVE_PINCH_REG.test(eventName)) {
          this._hammer.get("pinch").set({
            enable: true
          });
        }
        if (ACTIVE_ROTATE_REG.test(eventName)) {
          this._hammer.get("rotate").set({
            enable: true
          });
        }
        if (PAN_VERTICAL_events.indexOf(eventName) >= 0) {
          this._hammer.get("pan").set({
            direction: Hammer.DIRECTION_ALL
          });
        }
        if (SWIPE_VERTICAL_events.indexOf(eventName) >= 0) {
          this._hammer.get("swipe").set({
            direction: Hammer.DIRECTION_ALL
          });
        }
        this._hammer.on(eventConfig.hammerEvent, (function(_this) {
          return function(evt) {
            var arg;
            arg = {
              dom: _this._dom,
              event: evt,
              eventName: eventName
            };
            return _this.fire(eventName, _this, arg);
          };
        })(this));
        this._bindedEvents[eventName] = true;
        return;
      }
    };

    Widget.prototype._resetDimension = function() {
      var $dom, height, unit, width;
      $dom = this.get$Dom();
      unit = cola.constants.WIDGET_DIMENSION_UNIT;
      height = this.get("height");
      if (isFinite(height)) {
        height = "" + (parseInt(height)) + unit;
      }
      if (height) {
        $dom.css("height", height);
      }
      width = this.get("width");
      if (isFinite(width)) {
        width = "" + (parseInt(width)) + unit;
      }
      if (width) {
        $dom.css("width", width);
      }
    };

    Widget.prototype.showDimmer = function(options) {
      var $dom, content, dimmer, dimmerContent, k, v;
      if (options == null) {
        options = {};
      }
      if (!this._dom) {
        return this;
      }
      content = options.content;
      if (!content && this._dimmer) {
        content = this._dimmer.content;
      }
      if (content) {
        if (typeof content === "string") {
          dimmerContent = $.xCreate({
            tagName: "div",
            content: content
          });
        } else if (content.constructor === Object.prototype.constructor && content.tagName) {
          dimmerContent = $.xCreate(content);
        } else if (content.nodeType === 1) {
          dimmerContent = content;
        }
      }
      if (this._dimmer == null) {
        this._dimmer = {};
      }
      for (k in options) {
        v = options[k];
        if (k !== "content") {
          this._dimmer[k] = v;
        }
      }
      $dom = this.get$Dom();
      dimmer = $dom.dimmer("get dimmer");
      if (dimmerContent) {
        if (dimmer) {
          $(dimmer).empty();
        } else {
          $dom.dimmer("create");
        }
        $dom.dimmer("add content", dimmerContent);
      }
      $dom.dimmer(this._dimmer);
      $dom.dimmer("show");
      return this;
    };

    Widget.prototype.hideDimmer = function() {
      if (!this._dom) {
        return this;
      }
      this.get$Dom().dimmer("hide");
      return this;
    };

    Widget.prototype.destroy = function() {
      if (this._destroyed) {
        return;
      }
      if (this._dom) {
        delete this._hammer;
        delete this._bindedEvents;
        delete this._parent;
        delete this._doms;
      }
      Widget.__super__.destroy.call(this);
      this._destroyed = true;
    };

    return Widget;

  })(cola.RenderableElement);

  cola.floatWidget = {
    _zIndex: 1100,
    zIndex: function() {
      return ++cola.floatWidget._zIndex;
    }
  };

  cola.Exception.showException = function(ex) {
    var msg;
    if (ex instanceof cola.Exception || ex instanceof Error) {
      msg = ex.message;
    } else {
      msg = ex + "";
    }
    return cola.alert(msg);
  };

  cola.WidgetDataModel = (function(superClass) {
    extend(WidgetDataModel, superClass);

    function WidgetDataModel(model, widget1) {
      this.widget = widget1;
      WidgetDataModel.__super__.constructor.call(this, model);
    }

    WidgetDataModel.prototype.get = function(path, loadMode, context) {
      var ref;
      if (path.charCodeAt(0) === 64) {
        return this.widget.get(path.substring(1));
      } else {
        return (ref = this.model.parent) != null ? ref.data.get(path, loadMode, context) : void 0;
      }
    };

    WidgetDataModel.prototype.set = function(path, value) {
      var ref;
      if (path.charCodeAt(0) === 64) {
        this.widget.set(path.substring(1), value);
        this._onDataMessage(path.split("."), cola.constants.MESSAGE_PROPERTY_CHANGE, {});
      } else {
        if ((ref = this.model.parent) != null) {
          ref.data.set(path, value);
        }
      }
    };

    WidgetDataModel.prototype._processMessage = function(bindingPath, path, type, arg) {
      var attr, e, entity, isParent, ref, relativePath, targetPath;
      this._onDataMessage(path, type, arg);
      entity = arg.entity || arg.entityList;
      if (entity) {
        ref = this.widget._entityProps;
        for (attr in ref) {
          value = ref[attr];
          isParent = false;
          e = entity;
          while (e) {
            if (e === value) {
              isParent = true;
              break;
            }
            e = e.parent;
          }
          if (isParent) {
            targetPath = value.getPath();
            if (targetPath != null ? targetPath.length : void 0) {
              relativePath = path.slice(targetPath.length);
              this._onDataMessage(["@" + attr].concat(relativePath), type, arg);
            }
          }
        }
      }
    };

    WidgetDataModel.prototype.getDataType = function(path) {
      var ref;
      if (path.charCodeAt(0) === 64) {
        return null;
      } else {
        return (ref = this.model.parent) != null ? ref.data.getDataType(path) : void 0;
      }
    };

    WidgetDataModel.prototype.getProperty = function(path) {
      var ref;
      if (path.charCodeAt(0) === 64) {
        return null;
      } else {
        return (ref = this.model.parent) != null ? ref.data.getDataType(path) : void 0;
      }
    };

    WidgetDataModel.prototype.flush = function(name, loadMode) {
      var ref;
      if (path.charCodeAt(0) !== 64) {
        if ((ref = this.model.parent) != null) {
          ref.data.getDataType(name, loadMode);
        }
      }
      return this;
    };

    return WidgetDataModel;

  })(cola.AbstractDataModel);

  cola.WidgetModel = (function(superClass) {
    extend(WidgetModel, superClass);

    function WidgetModel(widget1, parent1) {
      var ref, widget;
      this.widget = widget1;
      this.parent = parent1;
      widget = this.widget;
      this.data = new cola.WidgetDataModel(this, widget);
      if ((ref = this.parent) != null) {
        ref.data.bind("**", this);
      }
      this.action = function(name) {
        var method;
        method = widget[name];
        if (method instanceof Function) {
          return function() {
            return method.apply(widget, arguments);
          };
        }
        return widget._scope.action(name);
      };
    }

    WidgetModel.prototype.repeatNotification = true;

    WidgetModel.prototype._processMessage = function(bindingPath, path, type, arg) {
      if (this.messageTimestamp >= arg.timestamp) {
        return;
      }
      return this.data._processMessage(bindingPath, path, type, arg);
    };

    return WidgetModel;

  })(cola.SubScope);

  cola.TemplateWidget = (function(superClass) {
    extend(TemplateWidget, superClass);

    function TemplateWidget() {
      return TemplateWidget.__super__.constructor.apply(this, arguments);
    }

    return TemplateWidget;

  })(cola.Widget);

}).call(this);
