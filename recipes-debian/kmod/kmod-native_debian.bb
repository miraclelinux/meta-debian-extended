# base recipe: meta/recipes-kernel/kmod/kmod_git.bb
# base branch: warrior
# base commit: 28cc7b4362c703d35916b22bb705bd9517e7c505

# Copyright (C) 2012 Khem Raj <raj.khem@gmail.com>
# Released under the MIT license (see COPYING.MIT for the terms)

require kmod.inc

DEPENDS += "zlib-native"

inherit native

do_install_append (){
	for tool in depmod insmod lsmod modinfo modprobe rmmod
	do
		ln -s kmod ${D}${bindir}/$tool
	done
}
