SUMMARY = "Embeddable SQL database engine"
HOMEPAGE = "http://www.sqlite.org"
SECTION = "libs"

LICENSE = "PD"

# we don't have sqlite3.h before run comile task.
# so, use base licene file instead.
LIC_FILES_CHKSUM = "file://autoconf/tea/license.terms;md5=5e0405ae78edb434653628790bb02b17"

PE = "3"
DEPENDS = "tcl tcl-native"

inherit debian-package
require recipes-debian/sources/sqlite3.inc

CVE_PRODUCT = "sqlite"

inherit autotools pkgconfig

# enable those which are enabled by default in configure
PACKAGECONFIG ?= "fts4 fts5 json1 rtree"

PACKAGECONFIG[editline] = "--enable-editline,--disable-editline,libedit"
PACKAGECONFIG[readline] = "--enable-readline,--disable-readline,readline ncurses"
PACKAGECONFIG[fts3] = "--enable-fts3,--disable-fts3"
PACKAGECONFIG[fts4] = "--enable-fts4,--disable-fts4"
PACKAGECONFIG[fts5] = "--enable-fts5,--disable-fts5"
PACKAGECONFIG[json1] = "--enable-json1,--disable-json1"
PACKAGECONFIG[rtree] = "--enable-rtree,--disable-rtree"
PACKAGECONFIG[session] = "--enable-session,--disable-session"

CFLAGS += "-DSQLITE_SECURE_DELETE -DSQLITE_ENABLE_COLUMN_METADATA"
CFLAGS += "-DSQLITE_ENABLE_FTS3 -DSQLITE_ENABLE_FTS3_PARENTHESIS"
CFLAGS += "-DSQLITE_ENABLE_RTREE=1 -DSQLITE_SOUNDEX=1"
CFLAGS += "-DSQLITE_ENABLE_UNLOCK_NOTIFY"
CFLAGS += "-DSQLITE_OMIT_LOOKASIDE=1 -DSQLITE_ENABLE_DBSTAT_VTAB"
CFLAGS += "-DSQLITE_ENABLE_UPDATE_DELETE_LIMIT=1"
CFLAGS += "-DSQLITE_ENABLE_LOAD_EXTENSION"
CFLAGS += "-DSQLITE_ENABLE_JSON1"
CFLAGS += "-DSQLITE_LIKE_DOESNT_MATCH_BLOBS"
CFLAGS += "-DSQLITE_THREADSAFE=1"
CFLAGS += "-DSQLITE_ENABLE_FTS3_TOKENIZER=1"
CFLAGS += "-DSQLITE_USE_URI=1"
CFLAGS += "-DSQLITE_MAX_SCHEMA_RETRY=25"
CFLAGS += "-DSQLITE_ENABLE_PREUPDATE_HOOK"
CFLAGS += "-DSQLITE_ENABLE_SESSION"
CFLAGS += "-DSQLITE_ENABLE_STMTVTAB"
CFLAGS += "-DSQLITE_MAX_VARIABLE_NUMBER=250000"
																	
EXTRA_OECONF = " \
    --enable-shared \
    --enable-threadsafe \
    --enable-load-extension \
    --with-tcl=${STAGING_DIR_TARGET}/usr/lib \
    --disable-tcl \
"
    
CFLAGS_append = " -fPIC -O2 -fno-strict-aliasing"

# pread() is in POSIX.1-2001 so any reasonable system must surely support it
CFLAGS += "-DUSE_PREAD"

# Provide column meta-data API
CFLAGS += "-DSQLITE_ENABLE_COLUMN_METADATA"

do_configure_append() {
  mv ${B}/${TARGET_PREFIX}libtool ${B}/libtool
}

do_install_append() {
  install -d ${D}/${mandir}/man1
  install -m 0644 ${S}/sqlite3.1 ${D}/${mandir}/man1
}

PACKAGES = "lib${BPN} lib${BPN}-dev lib${BPN}-doc ${PN}-dbg lib${BPN}-staticdev ${PN}"

FILES_${PN} = "${bindir}/*"
FILES_lib${BPN} = "${libdir}/*.so.*"
FILES_lib${BPN}-dev = "${libdir}/*.la ${libdir}/*.so \
                       ${libdir}/pkgconfig ${includedir}"
FILES_lib${BPN}-doc = "${docdir} ${mandir} ${infodir}"
FILES_lib${BPN}-staticdev = "${libdir}/lib*.a"

AUTO_LIBNAME_PKGS = "${MLPREFIX}lib${BPN}"
