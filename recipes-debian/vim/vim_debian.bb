# base recipe: poky/meta/recipes-support/vim/vim_8.1.1017.bb
# base branch: warrior
# base commit: c8c383d958807b991e31d22f612ba2a81a3860a4

SUMMARY = "Vi IMproved - enhanced vi editor"
SECTION = "console/utils"

PROVIDES = "xxd"
DEPENDS = "ncurses gettext-native"
# vimdiff doesn't like busybox diff
RSUGGESTS_${PN} = "diffutils"
LICENSE = "vim"
LIC_FILES_CHKSUM = "file://runtime/doc/uganda.txt;endline=288;md5=b6b102313797043756d35d1fc241592a"

inherit debian-package
require recipes-debian/sources/${BPN}.inc

VIMDIR = "vim${@d.getVar('PV').split('.')[0]}${@d.getVar('PV').split('.')[1]}"

inherit autotools-brokensep update-alternatives

CLEANBROKEN = "1"

# vim configure.in contains functions which got 'dropped' by autotools.bbclass
do_configure () {
    cd src
    rm -f auto/*
    touch auto/config.mk
    aclocal
    autoconf
    cd ..
    oe_runconf
    touch src/auto/configure
    touch src/auto/config.mk src/auto/config.h
}

#Available PACKAGECONFIG options are gtkgui, acl, x11, tiny
PACKAGECONFIG ??= ""
PACKAGECONFIG += " \
    ${@bb.utils.filter('DISTRO_FEATURES', 'acl selinux', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'x11 gtkgui', '', d)} \
"
PACKAGECONFIG_class-native = ""

PACKAGECONFIG[gtkgui] = "--enable-gui=gtk2,--enable-gui=no,gtk+,"
PACKAGECONFIG[acl] = "--enable-acl,--disable-acl,acl,"
PACKAGECONFIG[x11] = "--with-x,--without-x,xt,"
PACKAGECONFIG[tiny] = "--with-features=tiny,--with-features=big,,"
PACKAGECONFIG[selinux] = "--enable-selinux,--disable-selinux,libselinux,"
PACKAGECONFIG[elfutils] = "--enable-elf-check,,elfutils,"

EXTRA_OECONF = " \
    --disable-gpm \
    --disable-gtktest \
    --disable-xim \
    --disable-netbeans \
    --with-tlib=ncurses \
    ac_cv_small_wchar_t=no \
    vim_cv_getcwd_broken=no \
    vim_cv_memmove_handles_overlap=yes \
    vim_cv_stat_ignores_slash=no \
    vim_cv_terminfo=yes \
    vim_cv_tgetent=non-zero \
    vim_cv_toupper_broken=no \
    vim_cv_tty_group=world \
    STRIP=/bin/true \
"

do_install() {
    autotools_do_install

    # Work around file-rdeps picking up csh, awk, perl or python as a dep
    chmod -x ${D}${datadir}/${BPN}/${VIMDIR}/tools/vim132
    chmod -x ${D}${datadir}/${BPN}/${VIMDIR}/tools/mve.awk
    chmod -x ${D}${datadir}/${BPN}/${VIMDIR}/tools/*.pl
    chmod -x ${D}${datadir}/${BPN}/${VIMDIR}/tools/*.py

    # Install example vimrc from runtime files
    install -m 0644 runtime/vimrc_example.vim ${D}/${datadir}/${BPN}/vimrc

    # we use --with-features=big as default
    mv ${D}${bindir}/${BPN} ${D}${bindir}/${BPN}.${BPN}

    if ${@bb.utils.contains('DISTRO_FEATURES', 'x11', 'true', 'false', d)}; then
        # The mouse being autoenabled is just annoying in xfce4-terminal (mouse
        # drag make vim go into visual mode and there is no right click menu),
        # delete the block.
        sed -i '/the mouse works just fine/,+4d' ${D}/${datadir}/${BPN}/vimrc
    fi
}

PARALLEL_MAKEINST = ""

PACKAGES =+ "${PN}-common ${PN}-syntax ${PN}-help ${PN}-tutor ${PN}-vimrc ${PN}-tools"
FILES_${PN}-syntax = "${datadir}/${BPN}/${VIMDIR}/syntax"
FILES_${PN}-help = "${datadir}/${BPN}/${VIMDIR}/doc"
FILES_${PN}-tutor = "${datadir}/${BPN}/${VIMDIR}/tutor ${bindir}/${BPN}tutor"
FILES_${PN}-vimrc = "${datadir}/${BPN}/vimrc"
FILES_${PN}-data = "${datadir}/${BPN}"
FILES_${PN}-tools = "${datadir}/${BPN}/${VIMDIR}/tools"
FILES_${PN}-common = " \
    ${datadir}/${BPN}/${VIMDIR}/*.vim \
    ${datadir}/${BPN}/${VIMDIR}/autoload \
    ${datadir}/${BPN}/${VIMDIR}/colors \
    ${datadir}/${BPN}/${VIMDIR}/compiler \
    ${datadir}/${BPN}/${VIMDIR}/ftplugin \
    ${datadir}/${BPN}/${VIMDIR}/indent \
    ${datadir}/${BPN}/${VIMDIR}/keymap \
    ${datadir}/${BPN}/${VIMDIR}/lang \
    ${datadir}/${BPN}/${VIMDIR}/macros \
    ${datadir}/${BPN}/${VIMDIR}/plugin \
    ${datadir}/${BPN}/${VIMDIR}/print \
    ${datadir}/${BPN}/${VIMDIR}/spell \
    ${datadir}/icons \
"

RDEPENDS_${BPN} = "ncurses-terminfo-base"
# Recommend that runtime data is installed along with vim
RRECOMMENDS_${BPN} = "${PN}-syntax ${PN}-help ${PN}-tutor ${PN}-vimrc ${PN}-common"

ALTERNATIVE_${PN} = "vi vim xxd"
ALTERNATIVE_PRIORITY = "100"
ALTERNATIVE_TARGET = "${bindir}/${BPN}.${BPN}"
ALTERNATIVE_LINK_NAME[vi] = "${base_bindir}/vi"
ALTERNATIVE_LINK_NAME[vim] = "${bindir}/vim"
ALTERNATIVE_TARGET[xxd] = "${bindir}/xxd"
ALTERNATIVE_LINK_NAME[xxd] = "${bindir}/xxd"

BBCLASSEXTEND = "native"
